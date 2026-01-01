package com.hw.hwjobbackend.service.initialization;

import com.hw.hwjobbackend.model.dto.api.ProvinceApiResponse;
import com.hw.hwjobbackend.model.dto.response.file.FileResponse;
import com.hw.hwjobbackend.model.entity.industry.Industry;
import com.hw.hwjobbackend.model.entity.job_type.JobType;
import com.hw.hwjobbackend.model.entity.level.Level;
import com.hw.hwjobbackend.model.entity.skill.Skill;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import com.hw.hwjobbackend.model.entity.user.Role;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.model.enums.*;
import com.hw.hwjobbackend.model.enums.data.IndustryEnum;
import com.hw.hwjobbackend.model.enums.data.JobTypeEnum;
import com.hw.hwjobbackend.model.enums.data.LevelEnum;
import com.hw.hwjobbackend.model.enums.data.SkillEnum;
import com.hw.hwjobbackend.repository.http_client.RegionFeignClient;
import com.hw.hwjobbackend.repository.industry.IndustryRepository;
import com.hw.hwjobbackend.repository.job_type.JobTypeRepository;
import com.hw.hwjobbackend.repository.level.LevelRepository;
import com.hw.hwjobbackend.repository.region.RegionRepository;
import com.hw.hwjobbackend.repository.skill.SkillRepository;
import com.hw.hwjobbackend.repository.user.RoleRepository;
import com.hw.hwjobbackend.repository.user.UserRepository;
import com.hw.hwjobbackend.service.file.FileService;
import com.hw.hwjobbackend.service.shared.region.RegionService;
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
    RegionRepository provinceRepository;
    LevelRepository levelRepository;
    SkillRepository skillRepository;
    FileService fileService;


    PasswordEncoder passwordEncoder;
    RegionService regionService;
    RegionFeignClient regionFeignClient;

    @NonFinal
    @Value("${initial-app.admin.name}")
    String ADMIN_NAME;

    @NonFinal
    @Value("${initial-app.admin.username}")
    String ADMIN_USERNAME;

    @NonFinal
    @Value("${initial-app.admin.password}")
    String ADMIN_PASSWORD;

    @NonFinal
    @Value("${api.api-province}")
    String PROVINCE_API_URL;

    @Override
    @Transactional
    public void initializeRoles() {
        if (roleRepository.count() > 0) {
            return;
        }

        List<Role> roles = List.of(
                Role.builder()
                        .name(RoleEnum.ADMIN.name())
                        .description("Role Admin")
                        .build(),
                Role.builder()
                        .name(RoleEnum.CANDIDATE.name())
                        .description("Role Candidate")
                        .build(),
                Role.builder()
                        .name(RoleEnum.RECRUITER.name())
                        .description("Role Recruiter")
                        .build()
        );
        roleRepository.saveAll(roles);
    }

    @Override
    @Transactional
    public void createAdminUser() {

        Set<Role> roles = roleRepository.findAllByName(RoleEnum.ADMIN.name());

        if (userRepository.countUserByRoles(roles) > 0) {
            log.info("Admin user already exists. Skipping initialization.");
            return;
        }

        User adminUser = User.builder()
                .username(ADMIN_USERNAME)
                .fullName(ADMIN_NAME)
                .userStatus(UserStatusEnum.ACTIVE)
                .roles(roles)
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .build();
        User createdAdmin = userRepository.save(adminUser);
        FileResponse avatarResponse = fileService.setDefaultAvatarForUser(createdAdmin.getId());
        adminUser.setImageUrl(avatarResponse.getUrl());
    }

    @Override
    @Transactional
    public void initializeRegion() {
        if (provinceRepository.count() > 0) {
            return;
        }
        try {
            List<ProvinceApiResponse> provinceResponses = regionFeignClient.getAllProvinces();
            if (provinceResponses != null && !provinceResponses.isEmpty()) {
                for (ProvinceApiResponse provinceResponse : provinceResponses) {
                    regionService.createRegion(provinceResponse);
                }
            }
        } catch (Exception e) {
            log.error("Error initializing region data: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize region data from API", e);
        }
    }

    @Override
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
    @Transactional
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
    @Transactional
    public void initializeLevels() {
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

    @Override
    @Transactional
    public void initializeSkills() {
        if (skillRepository.count() > 0) {
            return;
        }
        Set<Skill> skills = Arrays.stream(SkillEnum.values())
                .map(s -> Skill.builder()
                        .name(s.getName())
                        .description(s.getDescription())
                        .build())
                .collect(Collectors.toSet());
        skillRepository.saveAll(skills);
    }

//    @Override
//    @Transactional
//    public void initializeTestUsers() {
//        // Check if test users already exist
//        if (userRepository.existsByUsername("candidate1") || userRepository.existsByUsername("recruiter1")) {
//            log.info("Test users already exist. Skipping initialization.");
//            return;
//        }
//
//        Set<Role> candidateRoles = roleRepository.findAllByName(RoleEnum.CANDIDATE.name());
//        Set<Role> recruiterRoles = roleRepository.findAllByName(RoleEnum.RECRUITER.name());
//
//        if (candidateRoles.isEmpty() || recruiterRoles.isEmpty()) {
//            log.error("Roles not found. Please initialize roles first.");
//            return;
//        }
//
//        String encodedPassword = passwordEncoder.encode("password");
//
//        // Create 100 candidates
//        List<User> candidates = new ArrayList<>();
//        for (int i = 1; i <= 100; i++) {
//            String username = "candidate" + i;
//            Candidate candidate = Candidate.builder()
//                    .username(username)
//                    .fullName(username)
//                    .email(username + "@email.com")
//                    .password(encodedPassword)
//                    .roles(candidateRoles)
//                    .userStatus(UserStatusEnum.ACTIVE)
//                    .build();
//            candidates.add(candidate);
//        }
//        userRepository.saveAll(candidates);
//        log.info("Created 100 candidate users");
//
//        // Set default avatar for candidates
//        for (User candidate : candidates) {
//            FileResponse avatarResponse = fileService.setDefaultAvatarForUser(candidate.getId());
//            candidate.setImageUrl(avatarResponse.getUrl());
//        }
//        userRepository.saveAll(candidates);
//
//        List<User> recruiters = new ArrayList<>();
//        for (int i = 1; i <= 100; i++) {
//            String username = "recruiter" + i;
//            Recruiter recruiter = Recruiter.builder()
//                    .username(username)
//                    .fullName(username)
//                    .email(username + "@email.com")
//                    .password(encodedPassword)
//                    .roles(recruiterRoles)
//                    .userStatus(UserStatusEnum.ACTIVE)
//                    .build();
//            recruiters.add(recruiter);
//        }
//        userRepository.saveAll(recruiters);
//        log.info("Created 100 recruiter users");
//
//        for (User recruiter : recruiters) {
//            FileResponse avatarResponse = fileService.setDefaultAvatarForUser(recruiter.getId());
//            recruiter.setImageUrl(avatarResponse.getUrl());
//        }
//        userRepository.saveAll(recruiters);
//
//        log.info("Test users initialization completed: 100 candidates + 100 recruiters");
//    }
}
