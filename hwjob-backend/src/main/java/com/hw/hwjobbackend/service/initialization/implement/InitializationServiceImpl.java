package com.hw.hwjobbackend.service.initialization.implement;

import com.hw.hwjobbackend.entity.*;
import com.hw.hwjobbackend.entity.user.Role;
import com.hw.hwjobbackend.entity.user.User;
import com.hw.hwjobbackend.enums.*;
import com.hw.hwjobbackend.repository.industry.IndustryRepository;
import com.hw.hwjobbackend.repository.job_type.JobTypeRepository;
import com.hw.hwjobbackend.repository.level.LevelRepository;
import com.hw.hwjobbackend.repository.region.ProvinceRepository;
import com.hw.hwjobbackend.repository.user.RoleRepository;
import com.hw.hwjobbackend.repository.user.UserRepository;
import com.hw.hwjobbackend.service.initialization.InitializationService;
import com.hw.hwjobbackend.service.region.RegionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InitializationServiceImpl implements InitializationService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    IndustryRepository industryRepository;
    JobTypeRepository jobTypeRepository;
    ProvinceRepository provinceRepository;
    LevelRepository levelRepository;


    RegionService regionService;
    PasswordEncoder passwordEncoder;

    @NonFinal
    @Value("${initial-app.admin.name}")
    String ADMIN_NAME;

    @NonFinal
    @Value("${initial-app.admin.username}")
    String ADMIN_USERNAME;

    @NonFinal
    @Value("${initial-app.admin.password}")
    String ADMIN_PASSWORD;

    @Override
    @Transactional
    public void initializeRolesAndAdmin() {
        if (userRepository.existsByUsername(ADMIN_USERNAME)) {
            log.info("Admin user already exists. Skipping initialization.");
            return;
        }
        initializeRoles();
        Role adminRole = roleRepository.findByName(RoleEnum.ADMIN.name())
                .orElseThrow(() -> new RuntimeException("Admin role not found after initialization."));

        createAdminUser(Set.of(adminRole));
        log.info("Predefined roles and admin user initialized successfully.");
    }

    @Override
    @Transactional
    public void initializeRegionData() {
        if (provinceRepository.count() == 0)
            regionService.initializeRegionData();
    }

    @Override
    @Transactional
    public void initializeRoles() {
        Map<String, String> roleMappings = Map.of(
                RoleEnum.RECRUITER.name(), "Role Recruiter",
                RoleEnum.CANDIDATE.name(), "Role Candidate",
                RoleEnum.ADMIN.name(), "Role Admin"
        );
        roleMappings.forEach(this::createRoleIfNotExists);
    }

    @Override
    @Transactional
    public void createAdminUser(Set<Role> roles) {
        User adminUser = User.builder()
                .username(ADMIN_USERNAME)
                .name(ADMIN_NAME)
                .userStatus(UserStatusEnum.ACTIVE)
                .roles(roles)
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .build();

        userRepository.save(adminUser);
    }

    @Transactional
    public void initializeIndustries() {
        if (industryRepository.count() > 0) {
            return;
        }
        List<Industry> industries = Arrays.stream(IndustryEnum.values())
                .map(i -> Industry.builder()
                        .name(i.getName())
                        .description(i.getDescription())
                        .build()
                ).collect(Collectors.toList());
        industryRepository.saveAll(industries);
    }


    @Override
    public void initializeJobTypes() {
        if (jobTypeRepository.count() > 0) {
            return;
        }
        List<JobType> jobTypes = Arrays.stream(JobTypeEnum.values())
                .map(j -> JobType.builder()
                        .name(j.getName())
                        .code(j.name())
                        .build())
                .collect(Collectors.toList());
        jobTypeRepository.saveAll(jobTypes);
    }

    @Override
    public void initializeLevel() {
        if (levelRepository.count() > 0) {
            return;
        }

        List<Level> levels = Arrays.stream(LevelEnum.values())
                .map(l -> Level.builder()
                        .name(l.getName())
                        .build())
                .collect(Collectors.toList());
        levelRepository.saveAll(levels);
    }

    private void createRoleIfNotExists(String name, String description) {
        roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = Role.builder()
                            .name(name)
                            .description(description)
                            .build();
                    Role savedRole = roleRepository.save(role);
                    log.debug("Created role: {}", name);
                    return savedRole;
                });
    }


}
