package com.hw.hwjobbackend.controller;

import com.hw.hwjobbackend.dto.request.CandidateUpdateRequest;
import com.hw.hwjobbackend.dto.request.RecruiterUpdateRequest;
import com.hw.hwjobbackend.dto.request.UserCreationRequest;
import com.hw.hwjobbackend.dto.response.*;
import com.hw.hwjobbackend.service.CandidateService;
import com.hw.hwjobbackend.service.RecruiterService;
import com.hw.hwjobbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserController {

    UserService userService;
    CandidateService candidateService;
    RecruiterService recruiterService;


    @PostMapping
    public ApiResponse<UserCreationResponse> create(
            @RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserCreationResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    // Get user info
    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserInfo())
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

    // Get all user
//    ApiResponse<UserResponse> getAllUser(){
//
//    }


}
