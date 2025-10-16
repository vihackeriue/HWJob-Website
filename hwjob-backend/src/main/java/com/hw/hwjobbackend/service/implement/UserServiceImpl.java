package com.hw.hwjobbackend.service.implement;

import com.hw.hwjobbackend.constant.PredefinedRole;
import com.hw.hwjobbackend.dto.request.UserCreationRequest;
import com.hw.hwjobbackend.dto.response.UserCreationResponse;
import com.hw.hwjobbackend.dto.response.UserResponse;
import com.hw.hwjobbackend.entity.Candidate;
import com.hw.hwjobbackend.entity.Recruiter;
import com.hw.hwjobbackend.entity.Role;
import com.hw.hwjobbackend.entity.User;
import com.hw.hwjobbackend.enums.UserStatusEnum;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.mapper.UserMapper;
import com.hw.hwjobbackend.repository.UserRepository;
import com.hw.hwjobbackend.service.RoleService;
import com.hw.hwjobbackend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
                .orElseThrow(() -> {
                    return new AppException(ErrorCode.USER_NOT_EXISTED);
                });

        return userMapper.toUserResponse(user);
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

            default -> {
                throw new AppException(ErrorCode.CREATE_USER_FAIL);
            }
        };
    }
}


