package com.hw.hwjobbackend.service.admin.admin_user;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.user.UserStatusRequest;
import com.hw.hwjobbackend.model.dto.response.user.UserResponse;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.model.enums.UserStatusEnum;
import com.hw.hwjobbackend.repository.user.UserRepository;
import com.hw.hwjobbackend.service.mapper.user.UserMapper;
import com.hw.hwjobbackend.util.PaginationUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserServiceImpl implements AdminUserService {

    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getUsers(int page, int size) {

        Pageable pageable = PaginationUtils.buildPageable(page, size);

        return userRepository.findAllOrderByCreatedAtDesc(pageable)
                .map(userMapper::toUserResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAllOrderByCreatedAtDesc()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    @Transactional
    public void changeUserStatus(String id, UserStatusRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        validateNotSelfUpdate(user.getId(), id);

        UserStatusEnum newStatus = request.getStatus();
        UserStatusEnum oldStatus = user.getUserStatus();

        if (oldStatus == newStatus) {
            return;
        }

        user.setUserStatus(newStatus);

    }

    private void validateNotSelfUpdate(String targetUserId, String currentUserId) {
        if (targetUserId.equals(currentUserId)) {
            throw new AppException(ErrorCode.CANNOT_CHANGE_OWN_STATUS);
        }
    }
}