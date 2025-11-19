package com.hw.hwjobbackend.controller;


import com.hw.hwjobbackend.dto.response.ApiResponse;
import com.hw.hwjobbackend.dto.response.user.RecruiterProfileResponse;
import com.hw.hwjobbackend.service.user.RecruiterService;
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
@RequestMapping("/profiles")
public class ProfileController {
    RecruiterService recruiterService;

    @GetMapping("/{id}")
    public ApiResponse<RecruiterProfileResponse> getRecruiterProfile(
            @PathVariable String id
    ) {
        return ApiResponse.<RecruiterProfileResponse>builder()
                .result(recruiterService.getRecruiterProfile(id))
                .build();
    }


}
