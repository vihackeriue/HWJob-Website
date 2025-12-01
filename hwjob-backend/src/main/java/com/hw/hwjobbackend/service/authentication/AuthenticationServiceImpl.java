package com.hw.hwjobbackend.service.authentication;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.authentication.AuthenticationRequest;
import com.hw.hwjobbackend.model.dto.response.authentication.AuthenticationResponse;
import com.hw.hwjobbackend.model.dto.response.user.UserLoginResponse;
import com.hw.hwjobbackend.model.entity.invalidate_token.InvalidateToken;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.repository.invalidate_token.RedisTokenRepository;
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

import java.util.Date;

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

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .authenticated(true)
                .user(userMapper.toUserLoginResponse(user))
                .build();
    }

    @Override
    public void logout(String token) {
        try {
            jwtService.verifyToken(token, false);

            jwtService.addToBlackList(SignedJWT.parse(token));

        } catch (Exception e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
}