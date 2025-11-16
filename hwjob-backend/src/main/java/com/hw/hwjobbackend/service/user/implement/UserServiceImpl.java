package com.hw.hwjobbackend.service.user.implement;

import com.hw.hwjobbackend.dto.request.user.UserStatusRequest;
import com.hw.hwjobbackend.enums.RoleEnum;
import com.hw.hwjobbackend.dto.request.user.UserCreationRequest;
import com.hw.hwjobbackend.dto.request.user.UserUpdateRequest;
import com.hw.hwjobbackend.dto.response.user.UserCreationResponse;
import com.hw.hwjobbackend.dto.response.user.UserResponse;
import com.hw.hwjobbackend.entity.*;
import com.hw.hwjobbackend.enums.UserStatusEnum;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.mapper.UserMapper;
import com.hw.hwjobbackend.repository.UserRepository;
import com.hw.hwjobbackend.service.user.RoleService;
import com.hw.hwjobbackend.service.region.RegionService;
import com.hw.hwjobbackend.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    RoleService roleService;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RegionService regionService;


    @Override
    @Transactional
    public UserCreationResponse createUser(UserCreationRequest request) {

        validateUserDoesNotExist(request.getUsername(), request.getEmail());

        Set<Role> roles = roleService.getRolesByNames(request.getRoles());

        String userType = determineUserType(roles);

        User user = createUserByType(userType, request, roles);

        User savedUser = userRepository.save(user);

        return userMapper.toUserCreationResponse(savedUser);
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
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserResponse> getAllUser(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).map(userMapper::toUserResponse);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
    }

    @Override
    public void updateRegion(User user, UserUpdateRequest request) {

        int provinceCode = request.getProvinceCode();

        if (provinceCode != 0) {
            Province province = regionService.getProvinceByCode(provinceCode);
            user.setProvince(province);
        } else {
            user.setProvince(null);
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void changeUserStatus(String id, UserStatusRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        user.setUserStatus(UserStatusEnum.valueOf(request.getStatus()));
        userRepository.save(user);
    }


    private void validateUserDoesNotExist(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        if (userRepository.existsByEmail(email)) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
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
                    .email(request.getEmail())
                    .password(encodedPassword)
                    .roles(roles)
                    .userStatus(UserStatusEnum.ACTIVE)
                    // Các field của Candidate
//                    .education("candidate education")
                    .build();

            case "RECRUITER" -> Recruiter.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(encodedPassword)
                    .roles(roles)
                    .userStatus(UserStatusEnum.ACTIVE)
                    // Các field của Recruiter
//                    .website("Recruiter website")
                    .build();

            default -> throw new AppException(ErrorCode.CREATE_USER_FAIL);
        };
    }
}

