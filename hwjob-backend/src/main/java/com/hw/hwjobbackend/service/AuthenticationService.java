package com.hw.hwjobbackend.service;

import com.hw.hwjobbackend.dto.request.AuthenticationRequest;
import com.hw.hwjobbackend.dto.request.IntrospectRequest;
import com.hw.hwjobbackend.dto.response.AuthenticationResponse;
import com.hw.hwjobbackend.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectResponse introspect(String token) throws ParseException, JOSEException;

    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse refreshToken(String token) throws ParseException, JOSEException;

    void logout(String token);
}
