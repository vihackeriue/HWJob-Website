package com.hw.hwjobbackend.service;


import com.hw.hwjobbackend.dto.request.UserCreationRequest;
import com.hw.hwjobbackend.dto.response.UserCreationResponse;
import com.hw.hwjobbackend.dto.response.UserResponse;


public interface UserService {
    UserCreationResponse createUser(UserCreationRequest request);
    UserResponse getMyInfo();
}
