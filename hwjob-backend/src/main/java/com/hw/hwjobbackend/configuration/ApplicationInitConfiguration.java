package com.hw.hwjobbackend.configuration;

import com.hw.hwjobbackend.constant.PredefinedRole;
import com.hw.hwjobbackend.entity.Role;
import com.hw.hwjobbackend.entity.User;
import com.hw.hwjobbackend.repository.RoleRepository;
import com.hw.hwjobbackend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfiguration {

    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${initial-app.admin.name}")
    String ADMIN_NAME;

    @NonFinal
    @Value("${initial-app.admin.username}")
    String ADMIN_USERNAME;

//    @NonFinal
//    @Value("${initial-app.admin.email}")
//    String ADMIN_EMAIL;

    @NonFinal
    @Value("${initial-app.admin.password}")
    String ADMIN_PASSWORD;

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(UserRepository userRepository,
                                        RoleRepository roleRepository) {
        return args -> {
            if (userRepository.findByUsername(ADMIN_USERNAME).isEmpty()) {
                roleRepository.save(
                        Role.builder()
                                .name(PredefinedRole.USER_ROLE)
                                .description("Role User")
                                .build()
                );
                Role adninRole = roleRepository.save(
                        Role.builder()
                                .name(PredefinedRole.ADMIN_ROLE)
                                .description("Role Admin")
                                .build());
                var roles = new HashSet<Role>();
                roles.add(adninRole);

                User user = User.builder()
                        .username(ADMIN_USERNAME)
                        .name(ADMIN_NAME)
                        .roles(roles)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created");
            }
            log.info("Application initialization completed .....");
        };
    }
}
