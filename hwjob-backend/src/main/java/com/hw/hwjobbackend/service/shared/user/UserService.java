package com.hw.hwjobbackend.service.shared.user;

import com.hw.hwjobbackend.model.dto.request.user.UserCreationRequest;
import com.hw.hwjobbackend.model.dto.request.user.UserUpdatePasswordRequest;
import com.hw.hwjobbackend.model.dto.response.loyalty_point.LoyaltyPointResponse;
import com.hw.hwjobbackend.model.dto.response.profile.CandidateProfileResponse;
import com.hw.hwjobbackend.model.dto.response.profile.RecruiterProfileResponse;
import com.hw.hwjobbackend.model.dto.response.user.*;
import com.hw.hwjobbackend.model.entity.user.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserCreationResponse createUser(UserCreationRequest request) throws Exception;

    UserResponse getUserInfo();



    void updatePassword(UserUpdatePasswordRequest request);

    UpdateAvatarResponse updateAvatar(MultipartFile file);

    RecruiterProfileResponse getRecruiterProfile(String id);

    CandidateProfileResponse getCandidateProfile(String id);

    void validateExistEmail(User user, String newEmail);

    void validateRegion(User user, Integer newRegionId);

}
