package com.hw.hwjobbackend.configuration;


import java.text.ParseException;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;

import com.hw.hwjobbackend.dto.request.IntrospectRequest;
import com.hw.hwjobbackend.dto.response.IntrospectResponse;
import com.hw.hwjobbackend.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

/**
 * CustomJwtDecoder:
 *  - Tùy chỉnh bộ giải mã (decoder) cho JWT trong Spring Security.
 *  - Xác thực token bằng cách gọi AuthenticationService.introspect() để kiểm tra hợp lệ.
 *  - Nếu token hợp lệ, dùng NimbusJwtDecoder để giải mã và xác minh chữ ký HS512.
 */

@Component
@Slf4j
public class CustomJwtDecoder implements org.springframework.security.oauth2.jwt.JwtDecoder {
    @Value("${jwt.signerKey}")
    private String signerKey;

    @Autowired
    private AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            var response = authenticationService
                    .introspect(token);
            if (!response.isValid()) throw new JwtException("Token invalid");
        } catch (ParseException | JOSEException e) {
            throw new JwtException(e.getMessage());
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
