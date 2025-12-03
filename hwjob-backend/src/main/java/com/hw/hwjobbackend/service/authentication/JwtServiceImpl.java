package com.hw.hwjobbackend.service.authentication;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.response.authentication.AuthenticationResponse;
import com.hw.hwjobbackend.model.dto.response.user.UserLoginResponse;
import com.hw.hwjobbackend.model.entity.token.InvalidateToken;
import com.hw.hwjobbackend.model.entity.token.RefreshToken;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.model.enums.TokenEnum;
import com.hw.hwjobbackend.model.enums.UserStatusEnum;
import com.hw.hwjobbackend.repository.token.InvalidateTokenRepository;
import com.hw.hwjobbackend.repository.token.RefreshTokenRepository;
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
    RefreshTokenRepository refreshTokenRepository;
    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public boolean introspect(String token) {
        try {
            verifyAccessToken(token);
            return true;
        } catch (Exception e) {
            log.debug("Token introspect failed: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public String generateAccessToken(User user) {
        Instant now = Instant.now();
        Instant expiry = now.plus(VALID_DURATION, ChronoUnit.SECONDS);
        String jwtId = UUID.randomUUID().toString();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expiry))
                .jwtID(jwtId)
                .claim("type", TokenEnum.ACCESS.name())
                .claim("username", user.getUsername())
                .claim("userFullName", user.getFullName())
                .claim("scope", buildScope(user))
                .build();

        return signToken(claimsSet);
    }

    @Override
    public String generateRefreshToken(User user) {
        Instant now = Instant.now();
        Instant expiry = now.plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS);
        String jwtId = UUID.randomUUID().toString();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(expiry))
                .jwtID(jwtId)
                .claim("type", TokenEnum.REFRESH.name())
                .build();

        String token = signToken(claimsSet);
        addToWhitelist(jwtId, user.getId(), expiry);
        return token;
    }

    @Override
    public AuthenticationResponse refreshToken(String refreshToken) throws ParseException, JOSEException {
        SignedJWT signedJWT = verifyRefreshToken(refreshToken);

        String userId = signedJWT.getJWTClaimsSet().getSubject();
        String oldJwtId = signedJWT.getJWTClaimsSet().getJWTID();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Xóa refresh token cũ và tạo token mới
        removeFromWhitelist(oldJwtId);
        String newAccessToken = generateAccessToken(user);
        String newRefreshToken = generateRefreshToken(user);

        UserLoginResponse userLoginResponse = userMapper.toUserLoginResponse(user);

        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .user(userLoginResponse)
                .build();
    }

    @Override
    public void addToBlacklist(String jwtId, Instant expiryTime) {
        long ttlSeconds = (expiryTime.toEpochMilli() - System.currentTimeMillis()) / 1000;

        if (ttlSeconds > 0) {
            InvalidateToken invalidateToken = InvalidateToken.builder()
                    .jwtId(jwtId)
                    .ttl(ttlSeconds)
                    .build();
            invalidateTokenRepository.save(invalidateToken);
            log.debug("Token added to blacklist: {}", jwtId);
        }
    }


    @Override
    public void addToWhitelist(String jwtId, String userId, Instant expiryTime) {
        long ttlSeconds = (expiryTime.toEpochMilli() - System.currentTimeMillis()) / 1000;

        if (ttlSeconds > 0) {
            RefreshToken refreshToken = RefreshToken.builder()
                    .jwtId(jwtId)
                    .userId(userId)
                    .ttl(ttlSeconds)
                    .build();
            refreshTokenRepository.save(refreshToken);
            log.debug("Token added to whitelist: {} for user: {}", jwtId, userId);
        }
    }

    @Override
    public void removeFromWhitelist(String jwtId) {
        refreshTokenRepository.deleteById(jwtId);
        log.debug("Token removed from whitelist: {}", jwtId);
    }

    @Override
    public SignedJWT verifyAccessToken(String token) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        if (!signedJWT.verify(verifier)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        String tokenType = (String) claims.getClaim("type");

        if (!TokenEnum.ACCESS.name().equals(tokenType)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (invalidateTokenRepository.existsById(claims.getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (claims.getExpirationTime().before(new Date())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        User user = userRepository.findById(claims.getSubject())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (user.getUserStatus() == UserStatusEnum.INACTIVE) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        return signedJWT;
    }

    @Override
    public SignedJWT verifyRefreshToken(String token) throws JOSEException, ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        if (!signedJWT.verify(verifier)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        String tokenType = (String) claims.getClaim("type");

        if (!TokenEnum.REFRESH.name().equals(tokenType)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (!refreshTokenRepository.existsById(claims.getJWTID())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (claims.getExpirationTime().before(new Date())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        User user = userRepository.findById(claims.getSubject())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (user.getUserStatus() == UserStatusEnum.INACTIVE) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }

        return signedJWT;
    }

    private String signToken(JWTClaimsSet claimsSet) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error while signing token", e);
            throw new AppException(ErrorCode.UNAUTHENTICATED);
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