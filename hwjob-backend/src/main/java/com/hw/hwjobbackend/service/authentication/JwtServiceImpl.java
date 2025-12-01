package com.hw.hwjobbackend.service.authentication;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.response.authentication.AuthenticationResponse;
import com.hw.hwjobbackend.model.dto.response.authentication.IntrospectResponse;
import com.hw.hwjobbackend.model.dto.response.user.UserLoginResponse;
import com.hw.hwjobbackend.model.entity.invalidate_token.InvalidateToken;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.model.enums.UserStatusEnum;
import com.hw.hwjobbackend.repository.invalidate_token.RedisTokenRepository;
import com.hw.hwjobbackend.repository.user.UserRepository;
import com.hw.hwjobbackend.service.mapper.user.UserMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JwtServiceImpl implements JwtService {

    @NonFinal
    @Value("${jwt.signerKey}")
    String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    long REFRESHABLE_DURATION;

    RedisTokenRepository redisTokenRepository;
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public IntrospectResponse introspect(String token) {
        try {
            verifyToken(token, false);
            return IntrospectResponse.builder()
                    .valid(true)
                    .build();
        } catch (Exception e) {
            return IntrospectResponse.builder()
                    .valid(false)
                    .build();
        }
    }

    @Override
    public String generateToken(User user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        Instant now = Instant.now();
        Instant expiry = now.plus(VALID_DURATION, ChronoUnit.SECONDS);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expiry))
                .jwtID(UUID.randomUUID().toString())
                .claim("username", user.getUsername())
                .claim("userFullName", user.getFullName())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    @Override
    public SignedJWT verifyToken(String token, boolean isRefresh)
            throws JOSEException, ParseException {

        SignedJWT signedJWT = SignedJWT.parse(token);

        // 1. verify signature
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        if (!signedJWT.verify(verifier)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // 2. check blacklist
        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        if (redisTokenRepository.existsById(jwtId)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // 3. check expiry
        Date expiryTime = isRefresh
                ? calculateRefreshExpiryTime(signedJWT)
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        if (expiryTime.before(new Date())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }


        // 4. validate user & status
        String userId = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (user.getUserStatus() == UserStatusEnum.INACTIVE) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        return signedJWT;
    }

    @Override
    public AuthenticationResponse refreshToken(String token)
            throws ParseException, JOSEException {

        SignedJWT signedJWT = verifyToken(token, true);

        addToBlackList(signedJWT);

        String userId = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String newToken = generateToken(user);
        UserLoginResponse userLoginResponse = userMapper.toUserLoginResponse(user);

        return AuthenticationResponse.builder()
                .token(newToken)
                .user(userLoginResponse)
                .authenticated(true)
                .build();
    }

    @Override
    public void addToBlackList(SignedJWT signedJWT) throws ParseException {
        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        long ttlSeconds = (expiryTime.getTime() - System.currentTimeMillis()) / 1000;

        if (ttlSeconds > 0) {
            InvalidateToken invalidateToken = InvalidateToken.builder()
                    .jwtId(jwtId)
                    .ttl(ttlSeconds)
                    .build();
            redisTokenRepository.save(invalidateToken);
        }
    }

    /**
     * Build scope string từ user roles
     */
    private String buildScope(User user) {
        StringJoiner scopeJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role ->
                    scopeJoiner.add("ROLE_" + role.getName().toUpperCase())
            );
        }

        return scopeJoiner.toString();
    }

    private Date calculateRefreshExpiryTime(SignedJWT signedJWT) throws ParseException {
        Instant issueTime = signedJWT.getJWTClaimsSet().getIssueTime().toInstant();
        Instant refreshExpiry = issueTime.plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS);
        return Date.from(refreshExpiry);
    }
}