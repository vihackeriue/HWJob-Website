package com.hw.hwjobbackend.service.authentication;

import com.hw.hwjobbackend.model.dto.request.authentication.AuthenticationRequest;
import com.hw.hwjobbackend.model.dto.response.authentication.AuthenticationResponse;
import com.hw.hwjobbackend.model.dto.response.user.UserLoginResponse;
import com.hw.hwjobbackend.model.entity.invalidate_token.InvalidateToken;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.mapper.user.UserMapper;
import com.hw.hwjobbackend.repository.token.RedisTokenRepository;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

//        ┌─────────────────────────────────────────────────────────────┐
//        │                         LOGIN FLOW                          │
//        └─────────────────────────────────────────────────────────────┘
//        1 User gửi request login với username + password
//
//        2  Spring Security gọi CustomUserDetailsService.loadUserByUsername()
//
//        3️  Tìm user trong DB qua UserRepository.findByUsername()
//
//                            ┌─────────────────────────┐
//                            │  User tồn tại không?    │
//                            └─────────────────────────┘
//                           Không                    Có
//
//                          Throw                  4️  Tạo CustomUserDetails(user)
//                UsernameNotFoundException
//                                                 5️  Spring Security verify password
//
//                                                 6️  CustomUserDetails.getAuthorities()
//                                                      lấy quyền hạn (roles)
//
//                                                 7️  Tạo JWT token với roles
//
//                                                 8️  Return token cho client

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {


    AuthenticationManager authenticationManager;
    UserMapper userMapper;

    JwtService jwtService;

    RedisTokenRepository redisTokenRepository;


    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        User user = (User) authenticate.getPrincipal();
        UserLoginResponse userLoginResponse = userMapper.toUserLoginResponse(user);
        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .user(userLoginResponse)
                .build();
    }

    @Override
    public void logout(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            String jit = signedJWT.getJWTClaimsSet().getJWTID();
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            InvalidateToken invalidateToken = InvalidateToken.builder()
                    .id(jit)
                    .expiredTime(expiryTime.getTime())
                    .build();
            redisTokenRepository.save(invalidateToken);
        } catch (ParseException exception) {
            log.info("Token already expired");
        }
    }
}
