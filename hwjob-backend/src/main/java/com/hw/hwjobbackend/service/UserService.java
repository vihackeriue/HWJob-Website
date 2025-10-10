package com.hw.hwjobbackend.service;


import com.hw.hwjobbackend.dto.request.UserCreationRequest;
import com.hw.hwjobbackend.dto.response.UserCreationResponse;
import org.springframework.stereotype.Service;


public interface UserService {
    UserCreationResponse createUser(UserCreationRequest request);
}
