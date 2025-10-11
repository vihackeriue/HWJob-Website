package com.hw.hwjobbackend.service;

import com.hw.hwjobbackend.dto.request.AuthenticationRequest;
import com.hw.hwjobbackend.dto.request.IntrospectRequest;
import com.hw.hwjobbackend.dto.request.LogoutRequest;
import com.hw.hwjobbackend.dto.request.RefreshRequest;
import com.hw.hwjobbackend.dto.response.AuthenticationResponse;
import com.hw.hwjobbackend.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request);

    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;

    void logout(String token);
}
