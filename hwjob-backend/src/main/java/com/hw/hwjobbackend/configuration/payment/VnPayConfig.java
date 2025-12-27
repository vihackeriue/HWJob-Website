package com.hw.hwjobbackend.configuration.payment;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "vnpay")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VnPayConfig {
     String tmnCode;
     String hashSecret;
     String payUrl;
     String returnUrl;
     String ipnUrl;
}
