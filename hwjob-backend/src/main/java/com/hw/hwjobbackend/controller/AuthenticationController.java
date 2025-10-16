package com.hw.hwjobbackend.controller;

import com.hw.hwjobbackend.dto.request.AuthenticationRequest;
import com.hw.hwjobbackend.dto.request.IntrospectRequest;
import com.hw.hwjobbackend.dto.response.ApiResponse;
import com.hw.hwjobbackend.dto.response.AuthenticationResponse;
import com.hw.hwjobbackend.dto.response.IntrospectResponse;
import com.hw.hwjobbackend.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.login(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        authenticationService.logout(token);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestHeader("Authorization") String authHeader) throws ParseException, JOSEException {
        String token = authHeader.substring(7);
        var result = authenticationService.introspect(token);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refresh(@RequestHeader("Authorization") String authHeader) throws ParseException, JOSEException {
        String token = authHeader.substring(7);
        var result = authenticationService.refreshToken(token);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }
}
