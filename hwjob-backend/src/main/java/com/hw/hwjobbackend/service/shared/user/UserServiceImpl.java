package com.hw.hwjobbackend.service.shared.user;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.user.UserCreationRequest;
import com.hw.hwjobbackend.model.dto.request.user.UserUpdatePasswordRequest;
import com.hw.hwjobbackend.model.dto.response.file.FileResponse;
import com.hw.hwjobbackend.model.dto.response.profile.CandidateProfileResponse;
import com.hw.hwjobbackend.model.dto.response.profile.RecruiterProfileResponse;
import com.hw.hwjobbackend.model.dto.response.user.UpdateAvatarResponse;
import com.hw.hwjobbackend.model.dto.response.user.UserCreationResponse;
import com.hw.hwjobbackend.model.dto.response.user.UserResponse;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import com.hw.hwjobbackend.model.entity.user.Role;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.model.enums.RoleEnum;
import com.hw.hwjobbackend.model.enums.UserStatusEnum;
import com.hw.hwjobbackend.repository.region.RegionRepository;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import com.hw.hwjobbackend.repository.user.RecruiterRepository;
import com.hw.hwjobbackend.repository.user.UserRepository;
import com.hw.hwjobbackend.service.file.FileService;
import com.hw.hwjobbackend.service.mapper.user.CandidateMapper;
import com.hw.hwjobbackend.service.mapper.user.RecruiterMapper;
import com.hw.hwjobbackend.service.mapper.user.UserMapper;
import com.hw.hwjobbackend.util.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleService roleService;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    FileService fileService;
    RecruiterMapper recruiterMapper;
    RecruiterRepository recruiterRepository;
    CandidateRepository candidateRepository;
    RegionRepository regionRepository;
    CandidateMapper candidateMapper;

    @Override
    @Transactional
    public UserCreationResponse createUser(UserCreationRequest request) {
        validateUserCreation(request);

        Set<Role> roles = roleService.getRolesByNames(request.getRoles());
        RoleEnum userType = determineUserType(roles);
        User user = createUserByType(userType, request, roles);


        user = userRepository.save(user);

        FileResponse avatarResponse = fileService.setDefaultAvatarForUser(user.getId());
        user.setImageUrl(avatarResponse.getUrl());

        return userMapper.toUserCreationResponse(user);
    }

    @Override
    public UserResponse getUserInfo() {
        String userId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserResponse userResponse = userMapper.toUserResponse(user);
        userResponse.setCompletionPercent(calculateCompletionPercent(user));
        return userResponse;
    }

    @Override
    public void validateExistEmail(User user, String newEmail) {
        if (newEmail == null || Objects.equals(user.getEmail(), newEmail)) {
            return;
        }
        if (userRepository.existsByEmail(newEmail)) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        user.setEmail(newEmail);
    }

    @Override
    public void validateRegion(User user, Integer newRegionId) {
        Integer currentRegionId = user.getRegion() != null ? user.getRegion().getId() : null;
        if (Objects.equals(currentRegionId, newRegionId)) {
            return;
        }
        if (!regionRepository.existsById(newRegionId)) {
            throw new AppException(ErrorCode.REGION_NOT_EXISTED);
        }
        user.setRegion(regionRepository.getReferenceById(newRegionId));
    }

    @Override
    @Transactional
    public void updatePassword(UserUpdatePasswordRequest request) {
        String userId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.OLD_PASSWORD_INVALID);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }

    @Override
    @Transactional
    public UpdateAvatarResponse updateAvatar(MultipartFile file) {

        String userId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Xóa avatar cũ nếu có
        if (user.getImageUrl() != null && !user.getImageUrl().isBlank()) {
            fileService.deleteFileByUrl(user.getImageUrl());
        }

        FileResponse response = fileService.uploadFile(file, user.getId());
        user.setImageUrl(response.getUrl());

        return UpdateAvatarResponse.builder()
                .imageUrl(user.getImageUrl())
                .build();
    }

    @Override
    public RecruiterProfileResponse getRecruiterProfile(String id) {
        Recruiter recruiter = recruiterRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        RecruiterProfileResponse response = recruiterMapper.toRecruiterProfileResponse(recruiter);
        response.setRegion(recruiter.getRegion() != null ? recruiter.getRegion().getName() : null);
        response.setFollowed(false);
        return response;
    }

    @Override
    public CandidateProfileResponse getCandidateProfile(String id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        CandidateProfileResponse response = candidateMapper.toCandidateProfileResponse(candidate);
        response.setRegion(candidate.getRegion() != null ? candidate.getRegion().getName() : null);

        return response;
    }

    // ===== PRIVATE HELPER METHODS =====

    private void validateUserCreation(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
    }

    private RoleEnum determineUserType(Set<Role> roles) {
        boolean hasCandidate = roles.stream()
                .anyMatch(role -> RoleEnum.CANDIDATE.name().equalsIgnoreCase(role.getName()));
        boolean hasRecruiter = roles.stream()
                .anyMatch(role -> RoleEnum.RECRUITER.name().equalsIgnoreCase(role.getName()));

        if (hasCandidate && hasRecruiter) {
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
        }
        if (hasCandidate) {
            return RoleEnum.CANDIDATE;
        }
        if (hasRecruiter) {
            return RoleEnum.RECRUITER;
        }
        throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
    }

    private User createUserByType(RoleEnum userType, UserCreationRequest request, Set<Role> roles) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        return switch (userType) {
            case CANDIDATE -> buildCandidate(request, encodedPassword, roles);
            case RECRUITER -> buildRecruiter(request, encodedPassword, roles);
            default -> throw new AppException(ErrorCode.CREATE_USER_FAIL);
        };
    }

    private Candidate buildCandidate(UserCreationRequest request, String encodedPassword, Set<Role> roles) {
        return Candidate.builder()
                .username(request.getUsername())
                .fullName(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .roles(roles)
                .userStatus(UserStatusEnum.ACTIVE)
                .build();
    }

    private Recruiter buildRecruiter(UserCreationRequest request, String encodedPassword, Set<Role> roles) {
        return Recruiter.builder()
                .username(request.getUsername())
                .fullName(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .roles(roles)
                .userStatus(UserStatusEnum.ACTIVE)
                .build();
    }

    private int calculateCompletionPercent(User user) {

        List<Supplier<Boolean>> checks = new ArrayList<>();

        checks.add(() -> hasText(user.getFullName()));
        checks.add(() -> hasText(user.getEmail()));
        checks.add(() -> hasText(user.getPhone()));
        checks.add(() -> user.getRegion() != null);
        checks.add(() -> hasText(user.getSummary()));
        checks.add(() -> hasText(user.getImageUrl()));

        if (user instanceof Candidate c) {
            checks.add(() -> c.getDob() != null);
            checks.add(() -> c.getGender() != null);
            checks.add(() -> hasText(c.getEducation()));
            checks.add(() -> c.getExpectSalary() != null);
            checks.add(() -> c.getSkills() != null && !c.getSkills().isEmpty());
        }

        if (user instanceof Recruiter r) {
            checks.add(() -> hasText(r.getWebsite()));
        }

        long completed = checks.stream()
                .filter(Supplier::get)
                .count();

        return Math.round(completed * 100f / checks.size());
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}