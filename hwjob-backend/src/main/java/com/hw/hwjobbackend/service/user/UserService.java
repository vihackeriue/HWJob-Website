package com.hw.hwjobbackend.service.user;


import com.hw.hwjobbackend.dto.request.UserCreationRequest;
import com.hw.hwjobbackend.dto.request.UserUpdateRequest;
import com.hw.hwjobbackend.dto.response.UserCreationResponse;
import com.hw.hwjobbackend.dto.response.UserResponse;
import com.hw.hwjobbackend.entity.User;

import java.util.List;

public interface UserService {
    UserCreationResponse createUser(UserCreationRequest request);

    UserResponse getUserInfo();

    List<UserResponse> getAllUser();

    void updatePassword(User user, String newPassword);

    void updateLocation(User user, UserUpdateRequest request);


}
