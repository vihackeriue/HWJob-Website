package com.hw.hwjobbackend.service.authentication;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.authentication.AuthenticationRequest;
import com.hw.hwjobbackend.model.dto.response.authentication.AuthenticationResponse;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.model.enums.TokenEnum;
import com.hw.hwjobbackend.service.mapper.user.UserMapper;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    AuthenticationManager authenticationManager;
    UserMapper userMapper;
    JwtService jwtService;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                );
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        log.info("User logged in successfully: {}", user.getUsername());

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(userMapper.toUserLoginResponse(user))
                .build();
    }

    @Override
    public void logout(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            String tokenType = (String) signedJWT.getJWTClaimsSet().getClaim("type");
            String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
            Instant expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime().toInstant();

            if (TokenEnum.REFRESH.name().equals(tokenType)) {
                jwtService.verifyRefreshToken(token);
                jwtService.removeFromWhitelist(jwtId);
                log.info("Refresh token removed for logout: {}", jwtId);
            } else if (TokenEnum.ACCESS.name().equals(tokenType)) {
                jwtService.verifyAccessToken(token);
                jwtService.addToBlacklist(jwtId, expiryTime);
                log.info("Access token blacklisted for logout: {}", jwtId);
            } else {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

        } catch (Exception e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
}