package com.hw.hwjobbackend.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Sử dụng cho @CreatedDate và @LastModifiedDate trong spring data JPA
 */

@Configuration
@EnableJpaAuditing
public class AuditingConfiguration {
}
