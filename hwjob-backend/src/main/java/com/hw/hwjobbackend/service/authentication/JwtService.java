package com.hw.hwjobbackend.service.authentication;

import com.hw.hwjobbackend.model.dto.request.authentication.RefreshTokenRequest;
import com.hw.hwjobbackend.model.dto.response.authentication.AuthenticationResponse;
import com.hw.hwjobbackend.model.entity.user.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.Date;

public interface JwtService {

    boolean introspect(String token);

    SignedJWT verifyAccessToken(String token, boolean isRefresh) throws JOSEException, ParseException;

    String generateAccessToken(User user);

    AuthenticationResponse refreshAccessToken(RefreshTokenRequest request) throws ParseException, JOSEException;

    void addToBlacklist(String jwtId, Date expiryTime);

}