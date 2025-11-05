package com.hw.hwjobbackend.service.initialization.implement;

import com.hw.hwjobbackend.enums.RoleEnum;
import com.hw.hwjobbackend.entity.Industry;
import com.hw.hwjobbackend.entity.Role;
import com.hw.hwjobbackend.entity.Skill;
import com.hw.hwjobbackend.entity.User;
import com.hw.hwjobbackend.enums.IndustryEnum;
import com.hw.hwjobbackend.enums.SkillEnum;
import com.hw.hwjobbackend.enums.UserStatusEnum;
import com.hw.hwjobbackend.repository.*;
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
    CountryRepository countryRepository;
    IndustryRepository industryRepository;
    SkillRepository skillRepository;
    RegionService locationService;
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
        initializePredefinedRoles();
        Role adminRole = roleRepository.findByName(RoleEnum.ADMIN.name())
                .orElseThrow(() -> new RuntimeException("Admin role not found after initialization."));

        createAdminUser(Set.of(adminRole));
        log.info("Predefined roles and admin user initialized successfully.");
    }

    @Override
    @Transactional
    public void initializeRegionData() {
        if (countryRepository.count() > 0) {
            return;
        }
        locationService.initializeRegionData();
    }

    @Override
    @Transactional
    public void initializePredefinedRoles() {
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
        Map<IndustryEnum, Industry> parentIndustries = Arrays.stream(IndustryEnum.values())
                .filter(e -> e.getParent() == null)
                .map(this::buildIndustry)
                .collect(Collectors.toMap(
                        industry -> findIndustryEnum(industry.getName()),
                        industryRepository::save
                ));
        List<Industry> childIndustries = Arrays.stream(IndustryEnum.values())
                .filter(e -> e.getParent() != null)
                .map(e -> buildIndustryWithParent(e, parentIndustries.get(e.getParent())))
                .collect(Collectors.toList());

        industryRepository.saveAll(childIndustries);
    }

    @Transactional
    public void initializeSkills() {
        if (skillRepository.count() > 0) {
            return;
        }
        List<Skill> skills = Arrays.stream(SkillEnum.values())
                .map(s -> Skill.builder().name(s.getName()).build())
                .collect(Collectors.toList());

        skillRepository.saveAll(skills);
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

    private Industry buildIndustry(IndustryEnum industryEnum) {
        return Industry.builder()
                .name(industryEnum.getName())
                .description(industryEnum.getDescription())
                .build();
    }

    private Industry buildIndustryWithParent(IndustryEnum industryEnum, Industry parent) {
        return Industry.builder()
                .name(industryEnum.getName())
                .description(industryEnum.getDescription())
                .parent(parent)
                .build();
    }

    private IndustryEnum findIndustryEnum(String name) {
        return Arrays.stream(IndustryEnum.values())
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Industry not found: " + name));
    }
}
