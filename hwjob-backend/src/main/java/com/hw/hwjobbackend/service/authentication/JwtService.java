package com.hw.hwjobbackend.service.authentication;

import com.hw.hwjobbackend.model.dto.response.authentication.AuthenticationResponse;
import com.hw.hwjobbackend.model.dto.response.authentication.IntrospectResponse;
import com.hw.hwjobbackend.model.entity.user.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public interface JwtService {

    IntrospectResponse introspect(String token);

    String generateToken(User user);

    SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException;

    AuthenticationResponse refreshToken(String token) throws ParseException, JOSEException;

    void addToBlackList(SignedJWT signedJWT) throws ParseException;
}
