package com.hw.hwjobbackend.service.shared.user;


import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.user.UserUpdatePasswordRequest;
import com.hw.hwjobbackend.model.dto.response.profile.CandidateProfileResponse;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import com.hw.hwjobbackend.service.mapper.user.CandidateMapper;
import com.hw.hwjobbackend.service.mapper.user.RecruiterMapper;
import com.hw.hwjobbackend.service.mapper.user.UserMapper;
import com.hw.hwjobbackend.model.dto.request.user.UserCreationRequest;
import com.hw.hwjobbackend.model.dto.request.user.UserUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.file.FileResponse;
import com.hw.hwjobbackend.model.dto.response.profile.RecruiterProfileResponse;
import com.hw.hwjobbackend.model.dto.response.user.*;
import com.hw.hwjobbackend.model.entity.region.Province;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import com.hw.hwjobbackend.model.entity.user.Role;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.model.enums.RoleEnum;
import com.hw.hwjobbackend.model.enums.UserStatusEnum;
import com.hw.hwjobbackend.repository.user.RecruiterRepository;
import com.hw.hwjobbackend.repository.user.UserRepository;
import com.hw.hwjobbackend.service.file.FileService;
import com.hw.hwjobbackend.service.shared.region.RegionService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleService roleService;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RegionService regionService;
    FileService fileService;
    RecruiterMapper recruiterMapper;
    RecruiterRepository recruiterRepository;
    CandidateRepository candidateRepository;
    CandidateMapper candidateMapper;


    @Override
    @Transactional
    public UserCreationResponse createUser(UserCreationRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        Set<Role> roles = roleService.getRolesByNames(request.getRoles());

        String userType = determineUserType(roles);

        User user = createUserByType(userType, request, roles);
        // Gán avatar mặc định cho user mới tạo
        FileResponse avatarResponse = fileService
                .setDefaultAvatarForUser(user.getUsername());
        user.setImageUrl(avatarResponse.getUrl());

        userRepository.save(user);
        return userMapper.toUserCreationResponse(user);
    }

    @Override
    public UserResponse getUserInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @Override
    public void validateEmail(String currentEmail, String newEmail) {
        if (newEmail.equals(currentEmail)) {
            if (userRepository.existsByEmail(newEmail)) {
                throw new AppException(ErrorCode.EMAIL_EXISTED);
            }
        }
    }

    @Override
    @Transactional
    public void updatePassword(UserUpdatePasswordRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.OLD_PASSWORD_INVALID);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateRegion(User user, UserUpdateRequest request) {
        Integer provinceCode = request.getRegionId();
        if (provinceCode != 0) {
            Province province = regionService.getProvinceByCode(provinceCode);
            user.setProvince(province);
        } else {
            user.setProvince(null);
        }
    }

    @Override
    public UpdateAvatarResponse updateAvatar(MultipartFile file) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        // Xóa avatar cũ nếu có
        if (user.getImageUrl() != null && !user.getImageUrl().isBlank()) {
            fileService.deleteFileByUrl(user.getImageUrl());
        }

        FileResponse response = fileService.uploadFile(file);

        user.setImageUrl(response.getUrl());

        userRepository.save(user);

        return UpdateAvatarResponse.builder()
                .imageUrl(user.getImageUrl())
                .build();
    }

    @Override
    public RecruiterProfileResponse getRecruiterProfile(String id) {
        Recruiter recruiter = recruiterRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        RecruiterProfileResponse response = recruiterMapper.toRecruiterProfileResponse(recruiter);
        response.setRegion(recruiter.getProvince() != null ? recruiter.getProvince().getName() : null);
        response.setFollowed(false);
        return response;
    }

    @Override
    public CandidateProfileResponse getCandidateProfile(String id) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        CandidateProfileResponse response = candidateMapper.toCandidateProfileResponse(candidate);
        response.setRegion(candidate.getProvince() != null ? candidate.getProvince().getName() : null);
        return response;
    }

    private String determineUserType(Set<Role> roles) {
        boolean hasCandidate = roles.stream()
                .anyMatch(r -> RoleEnum.CANDIDATE.name().equalsIgnoreCase(r.getName()));
        boolean hasRecruiter = roles.stream()
                .anyMatch(r -> RoleEnum.RECRUITER.name().equalsIgnoreCase(r.getName()));

        if (hasCandidate && hasRecruiter) {
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
        }
        if (hasCandidate) return RoleEnum.CANDIDATE.name();
        if (hasRecruiter) return RoleEnum.RECRUITER.name();

        throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
    }

    private User createUserByType(String userType, UserCreationRequest request, Set<Role> roles) {

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        return switch (userType) {
            case "CANDIDATE" -> Candidate.builder()
                    .username(request.getUsername())
                    .fullName(request.getUsername())
                    .email(request.getEmail())
                    .password(encodedPassword)
                    .roles(roles)
                    .userStatus(UserStatusEnum.ACTIVE)
                    .build();

            case "RECRUITER" -> Recruiter.builder()
                    .username(request.getUsername())
                    .fullName(request.getUsername())
                    .email(request.getEmail())
                    .password(encodedPassword)
                    .roles(roles)
                    .userStatus(UserStatusEnum.ACTIVE)
                    .build();

            default -> throw new AppException(ErrorCode.CREATE_USER_FAIL);
        };
    }


}
