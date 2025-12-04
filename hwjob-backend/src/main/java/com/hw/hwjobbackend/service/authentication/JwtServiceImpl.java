package com.hw.hwjobbackend.service.authentication;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.authentication.RefreshTokenRequest;
import com.hw.hwjobbackend.model.dto.response.authentication.AuthenticationResponse;
import com.hw.hwjobbackend.model.entity.token.InvalidateToken;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.repository.token.InvalidateTokenRepository;
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

    InvalidateTokenRepository invalidateTokenRepository;
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public boolean introspect(String token) {
        try {
            verifyAccessToken(token, false);
            return true;
        } catch (Exception e) {
            log.debug("Token introspect failed: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public SignedJWT verifyAccessToken(String token, boolean isRefresh) throws JOSEException, ParseException {

        SignedJWT signedJWT = SignedJWT.parse(token);

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        boolean verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();

        if (invalidateTokenRepository.existsById(jwtId)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    @Override
    public String generateAccessToken(User user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        Date now = new Date();
        Date expiry = new Date(Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli());
        String jwtId = UUID.randomUUID().toString();

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId())
                .issuer("hwjob")
                .issueTime(now)
                .expirationTime(expiry)
                .jwtID(jwtId)
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
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public AuthenticationResponse refreshAccessToken(RefreshTokenRequest request) throws ParseException, JOSEException {

        SignedJWT signedJWT = verifyAccessToken(request.getToken(), true);

        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        addToBlacklist(jit, expiryTime);

        String userId = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String newToken = generateAccessToken(user);
        return AuthenticationResponse.builder()
                .accessToken(newToken)
                .user(userMapper.toUserLoginResponse(user))
                .build();

    }

    @Override
    public void addToBlacklist(String jwtId, Date expiryTime) {
        long ttlSeconds = (expiryTime.getTime() - System.currentTimeMillis()) / 1000;
        if (ttlSeconds > 0) {
            InvalidateToken invalidateToken = InvalidateToken.builder()
                    .jwtId(jwtId)
                    .ttl(ttlSeconds)
                    .build();
            invalidateTokenRepository.save(invalidateToken);
            log.debug("Token added to blacklist: {}", jwtId);
        }
    }

    private String buildScope(User user) {
        StringJoiner scopeJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role ->
                    scopeJoiner.add("ROLE_" + role.getName().toUpperCase())
            );
        }
        return scopeJoiner.toString();
    }
}