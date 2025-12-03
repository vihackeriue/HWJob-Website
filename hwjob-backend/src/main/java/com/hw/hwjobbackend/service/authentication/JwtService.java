package com.hw.hwjobbackend.service.authentication;

import com.hw.hwjobbackend.model.dto.response.authentication.AuthenticationResponse;
import com.hw.hwjobbackend.model.entity.user.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.time.Instant;

public interface JwtService {

    boolean introspect(String token);

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    SignedJWT verifyAccessToken(String token) throws JOSEException, ParseException;

    SignedJWT verifyRefreshToken(String token) throws JOSEException, ParseException;

    AuthenticationResponse refreshToken(String refreshToken) throws ParseException, JOSEException;

    void addToBlacklist(String jwtId, Instant expiryTime);

    void addToWhitelist(String jwtId, String userId, Instant expiryTime);

    void removeFromWhitelist(String jwtId);
}