package com.hw.hwjobbackend.configuration.security;

import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;

import com.hw.hwjobbackend.service.authentication.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

/**
 * CustomJwtDecoder:
 * - Tùy chỉnh decoder cho JWT trong Spring Security để sử dụng signerKey từ cấu hình.
 * - Dùng NimbusJwtDecoder để giải mã và xác minh chữ ký HS512.
 * - Chỉ xác thực Access Token, không cho phép Refresh Token qua filter này.
 */
@Component
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {

    @Value("${jwt.signerKey}")
    private String signerKey;

    private NimbusJwtDecoder nimbusJwtDecoder;

    private final JwtService jwtService;

    @Override
    public Jwt decode(String token) throws JwtException {
        if (!jwtService.introspect(token)) {
            throw new JwtException("Invalid access token");
        }
        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }
        return nimbusJwtDecoder.decode(token);
    }
}