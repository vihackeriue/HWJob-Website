
package com.hw.hwjobbackend.configuration;

import com.hw.hwjobbackend.service.InitializationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class để khởi tạo dữ liệu ban đầu khi application start
 */
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfiguration {

    InitializationService initializationService;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner() {
        return args -> {
            long startTime = System.currentTimeMillis();
            log.info("=== Starting application initialization ===");

            try {
                // Khởi tạo roles và admin user
                initializationService.initializeRolesAndAdmin();

                // Khởi tạo location data
                initializationService.initializeLocationData();

                // Khởi tạo industries
                initializationService.initializeIndustries();

                // Khởi tạo skills
                initializationService.initializeSkills();

                long duration = System.currentTimeMillis() - startTime;
                log.info("=== Application initialization completed in {}ms ===", duration);
            } catch (Exception e) {
                log.error("Application initialization failed", e);
                throw new RuntimeException("Failed to initialize application", e);
            }
        };
    }
}