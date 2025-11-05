package com.hw.hwjobbackend.controller;

import com.hw.hwjobbackend.dto.request.CandidateUpdateRequest;
import com.hw.hwjobbackend.dto.request.RecruiterUpdateRequest;
import com.hw.hwjobbackend.dto.request.UserCreationRequest;
import com.hw.hwjobbackend.dto.response.*;
import com.hw.hwjobbackend.service.user.CandidateService;
import com.hw.hwjobbackend.service.user.RecruiterService;
import com.hw.hwjobbackend.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserController {

    UserService userService;
    CandidateService candidateService;
    RecruiterService recruiterService;

    // Get user info
    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserInfo())
                .build();
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUser() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUser())
                .build();
    }

    @PostMapping
    public ApiResponse<UserCreationResponse> create(
            @RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserCreationResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @PutMapping("/candidates/{id}")
    ApiResponse<CandidateResponse> updateCandidate(
            @PathVariable String id,
            @RequestBody @Valid CandidateUpdateRequest request) {
        return ApiResponse.<CandidateResponse>builder()
                .result(candidateService.updateCandidateInfo(id, request))
                .build();
    }

    @PutMapping("/recruiters/{id}")
    ApiResponse<RecruiterResponse> updateRecruiter(
            @PathVariable String id,
            @RequestBody @Valid RecruiterUpdateRequest request
    ) {
        return ApiResponse.<RecruiterResponse>builder()
                .result(recruiterService.updateRecruiterInfo(id, request))
                .build();
    }
}
