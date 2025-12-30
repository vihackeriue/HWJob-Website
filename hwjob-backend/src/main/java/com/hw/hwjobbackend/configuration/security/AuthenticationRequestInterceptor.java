package com.hw.hwjobbackend.configuration.security;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j
public class AuthenticationRequestInterceptor implements RequestInterceptor {


    @Value("${jwt.api-key}")
    private String apiKey;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (StringUtils.hasText(apiKey)) {
            requestTemplate.header("Api-key", apiKey);
        }
    }
}
