package com.hw.hwjobbackend.controller.common;

import com.hw.hwjobbackend.model.dto.request.authentication.AuthenticationRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.authentication.AuthenticationResponse;
import com.hw.hwjobbackend.model.dto.response.authentication.IntrospectResponse;
import com.hw.hwjobbackend.service.authentication.AuthenticationService;
import com.hw.hwjobbackend.service.authentication.JwtService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    AuthenticationService authenticationService;
    JwtService jwtService;

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
        var result = jwtService.introspect(token);
        return ApiResponse.<IntrospectResponse>builder().result(result).build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refresh(@RequestHeader("Authorization") String authHeader) throws ParseException, JOSEException {
        String token = authHeader.substring(7);
        var result = jwtService.refreshToken(token);
        return ApiResponse.<AuthenticationResponse>builder().result(result).build();
    }


}
