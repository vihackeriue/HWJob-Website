package com.hw.hwjobbackend.controller.authentication;

import com.hw.hwjobbackend.model.dto.request.authentication.AuthenticationRequest;
import com.hw.hwjobbackend.model.dto.request.authentication.RefreshTokenRequest;
import com.hw.hwjobbackend.model.dto.request.user.UserCreationRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.authentication.AuthenticationResponse;
import com.hw.hwjobbackend.model.dto.response.user.UserCreationResponse;
import com.hw.hwjobbackend.service.authentication.AuthenticationService;
import com.hw.hwjobbackend.service.authentication.JwtService;
import com.hw.hwjobbackend.service.shared.user.UserService;
import com.hw.hwjobbackend.util.TokenUtils;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
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
    UserService userService;


    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.login(request))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<UserCreationResponse> register(
            @Valid @RequestBody UserCreationRequest request) throws Exception {
        return ApiResponse.<UserCreationResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String token = TokenUtils.extractToken(authHeader);
        authenticationService.logout(token);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(jwtService.refreshAccessToken(request))
                .build();
    }
}