package com.hw.hwjobbackend.service.user;


import com.hw.hwjobbackend.model.dto.request.user.UserCreationRequest;
import com.hw.hwjobbackend.model.dto.request.user.UserStatusRequest;
import com.hw.hwjobbackend.model.dto.request.user.UserUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.user.UpdateAvatarResponse;
import com.hw.hwjobbackend.model.dto.response.user.UserCreationResponse;
import com.hw.hwjobbackend.model.dto.response.user.UserResponse;
import com.hw.hwjobbackend.model.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    UserCreationResponse createUser(UserCreationRequest request);

    UserResponse getUserInfo();

    Page<UserResponse> getUsers(int page, int size);

    List<UserResponse> getAllUsers();

    void updatePassword(User user, String newPassword);

    void updateRegion(User user, UserUpdateRequest request);

    void changeUserStatus(String id, UserStatusRequest request);

    UpdateAvatarResponse updateAvatar(MultipartFile file);

}
