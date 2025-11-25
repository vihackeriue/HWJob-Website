package com.hw.hwjobbackend.service.authentication;

import com.hw.hwjobbackend.model.dto.request.authentication.AuthenticationRequest;
import com.hw.hwjobbackend.model.dto.response.authentication.AuthenticationResponse;
import com.hw.hwjobbackend.model.dto.response.authentication.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    void logout(String token);

}
