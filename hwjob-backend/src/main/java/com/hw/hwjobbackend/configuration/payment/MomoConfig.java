package com.hw.hwjobbackend.configuration.payment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "momo")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MomoConfig {
    String partnerCode;
    String accessKey;
    String secretKey;
    String endpoint;
    String redirectUrl;
    String ipnUrl;
}
