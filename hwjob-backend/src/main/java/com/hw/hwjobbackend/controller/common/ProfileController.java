package com.hw.hwjobbackend.controller.common;

import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.profile.CandidateProfileResponse;
import com.hw.hwjobbackend.model.dto.response.profile.RecruiterProfileResponse;
import com.hw.hwjobbackend.service.shared.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("common/profiles")
public class ProfileController {

    UserService userService;

    @GetMapping("/recruiter-profiles/{id}")
    public ApiResponse<RecruiterProfileResponse> getRecruiterProfile(
            @PathVariable String id
    ) {
        return ApiResponse.<RecruiterProfileResponse>builder()
                .result(userService.getRecruiterProfile(id))
                .build();
    }

    @GetMapping("/candidate-profiles/{id}")
    public ApiResponse<CandidateProfileResponse> getCandidateProfile(
            @PathVariable String id
    ) {
        return ApiResponse.<CandidateProfileResponse>builder()
                .result(userService.getCandidateProfile(id))
                .build();
    }
}
