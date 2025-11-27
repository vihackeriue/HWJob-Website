package com.hw.hwjobbackend.service.shared.user;

import com.hw.hwjobbackend.model.dto.request.user.UserCreationRequest;
import com.hw.hwjobbackend.model.dto.request.user.UserUpdatePasswordRequest;
import com.hw.hwjobbackend.model.dto.request.user.UserUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.profile.CandidateProfileResponse;
import com.hw.hwjobbackend.model.dto.response.profile.RecruiterProfileResponse;
import com.hw.hwjobbackend.model.dto.response.user.*;
import com.hw.hwjobbackend.model.entity.user.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserCreationResponse createUser(UserCreationRequest request);

    UserResponse getUserInfo();

    void validateEmail(String currentEmail, String newEmail);

    void updatePassword(UserUpdatePasswordRequest request);

    void updateRegion(User user, UserUpdateRequest request);

    UpdateAvatarResponse updateAvatar(MultipartFile file);

    RecruiterProfileResponse getRecruiterProfile(String id);

    CandidateProfileResponse getCandidateProfile(String id);

}
