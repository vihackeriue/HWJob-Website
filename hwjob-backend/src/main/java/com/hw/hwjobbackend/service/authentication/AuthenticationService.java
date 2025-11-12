package com.hw.hwjobbackend.service.authentication;

import com.hw.hwjobbackend.dto.request.authentication.AuthenticationRequest;
import com.hw.hwjobbackend.dto.response.authentication.AuthenticationResponse;
import com.hw.hwjobbackend.dto.response.authentication.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectResponse introspect(String token) throws ParseException, JOSEException;

    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse refreshToken(String token) throws ParseException, JOSEException;

    void logout(String token);
}
