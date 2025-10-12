package com.hw.hwjobbackend.service.implement;

import com.hw.hwjobbackend.configuration.Translator;
import com.hw.hwjobbackend.dto.request.AuthenticationRequest;
import com.hw.hwjobbackend.dto.request.IntrospectRequest;
import com.hw.hwjobbackend.dto.response.AuthenticationResponse;
import com.hw.hwjobbackend.dto.response.IntrospectResponse;
import com.hw.hwjobbackend.entity.InvalidateToken;
import com.hw.hwjobbackend.entity.User;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.repository.RedisTokenRepository;
import com.hw.hwjobbackend.repository.UserRepository;
import com.hw.hwjobbackend.service.AuthenticationService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class AuthenticationServiceImpl implements AuthenticationService {

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    RedisTokenRepository redisTokenRepository;
    UserRepository userRepository;


    @Override
    public IntrospectResponse introspect(String token) throws ParseException, JOSEException {
        boolean isValid = true;
        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }
        return IntrospectResponse.builder().valid(isValid).build();
    }


    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_PASSWORD_INVALID));
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) throw new AppException(ErrorCode.USERNAME_PASSWORD_INVALID);
        var token = generateToken(user);
        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    @Override
    public void logout(String token) {
        try {
            var signToken = verifyToken(token, true);
            String jit = String.valueOf(signToken.getJWTClaimsSet().getJWTID());
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
            InvalidateToken invalidateToken = InvalidateToken.builder()
                    .id(jit)
                    .expiredTime(expiryTime.getTime())
                    .build();
            redisTokenRepository.save(invalidateToken);
        } catch (AppException | ParseException | JOSEException exception) {
            log.info("Token already expired");
        }
    }

    @Override
    public AuthenticationResponse refreshToken(String token) throws ParseException, JOSEException {
        var signedJWT = verifyToken(token, true);
        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidateToken invalidateToken = InvalidateToken.builder()
                .id(jit)
                .expiredTime(expiryTime.getTime())
                .build();
        redisTokenRepository.save(invalidateToken);
        var userName = signedJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        var newToken = generateToken(user);
        return AuthenticationResponse.builder().token(newToken).authenticated(true).build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                .getJWTClaimsSet()
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (redisTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }


    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        Instant now = Instant.now();
        Instant expiry = now.plus(VALID_DURATION, ChronoUnit.SECONDS);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expiry))
                .jwtID(UUID.randomUUID().toString())
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
