package com.hw.hwjobbackend.service.admin.admin_user;

import com.hw.hwjobbackend.model.dto.request.user.UserStatusRequest;
import com.hw.hwjobbackend.model.dto.response.user.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminUserService {

    Page<UserResponse> getAllUsers(int page, int size);

    List<UserResponse> getAllUsers();

    void changeUserStatus(String id, UserStatusRequest request);
}
