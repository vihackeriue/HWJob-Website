package com.hw.hwjobbackend.service.user;


import com.hw.hwjobbackend.dto.request.user.UserCreationRequest;
import com.hw.hwjobbackend.dto.request.user.UserStatusRequest;
import com.hw.hwjobbackend.dto.request.user.UserUpdateRequest;
import com.hw.hwjobbackend.dto.response.user.UserCreationResponse;
import com.hw.hwjobbackend.dto.response.user.UserResponse;
import com.hw.hwjobbackend.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {

    UserCreationResponse createUser(UserCreationRequest request);

    UserResponse getUserInfo();

    Page<UserResponse> getAllUser(int page, int size);

    void updatePassword(User user, String newPassword);

    void updateRegion(User user, UserUpdateRequest request);

    void changeUserStatus(String id, UserStatusRequest request);

}
