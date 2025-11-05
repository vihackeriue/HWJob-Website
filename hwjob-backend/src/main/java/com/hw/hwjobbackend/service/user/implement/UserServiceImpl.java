package com.hw.hwjobbackend.service.user.implement;

import com.hw.hwjobbackend.constant.PredefinedRole;
import com.hw.hwjobbackend.dto.request.UserCreationRequest;
import com.hw.hwjobbackend.dto.request.UserUpdateRequest;
import com.hw.hwjobbackend.dto.response.UserCreationResponse;
import com.hw.hwjobbackend.dto.response.UserResponse;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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
    //    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUserInfo() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }
    }

    @Override
    public void updateLocation(User user, UserUpdateRequest request) {
        String countryCode = request.getCountryCode();
        int provinceCode = request.getProvinceCode();
        int wardCode = request.getWardCode();

        if (countryCode != null && !countryCode.isEmpty()) {
            Country country = regionService.getCountryByCode(countryCode);
            user.setCountry(country);

            if (provinceCode != 0) {
                Province province = regionService.getProvinceByCodeAndCountry(provinceCode, country);
                user.setProvince(province);

                if (wardCode != 0) {
                    Ward ward = regionService.getWardByCodeAndProvince(wardCode, province);
                    user.setWard(ward);
                } else {
                    user.setWard(null);
                }
            } else {
                user.setProvince(null);
                user.setWard(null);
            }
        }
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
                .anyMatch(r -> PredefinedRole.CANDIDATE_ROLE.equals(r.getName()));
        boolean hasRecruiter = roles.stream()
                .anyMatch(r -> PredefinedRole.RECRUITER_ROLE.equals(r.getName()));

        if (hasCandidate && hasRecruiter) {
            throw new AppException(ErrorCode.ROLE_NOT_EXISTED);
        }
        if (hasCandidate) return PredefinedRole.CANDIDATE_ROLE;
        if (hasRecruiter) return PredefinedRole.RECRUITER_ROLE;

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
