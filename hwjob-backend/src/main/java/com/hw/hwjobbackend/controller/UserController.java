package com.hw.hwjobbackend.controller;

import com.hw.hwjobbackend.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.dto.request.user.RecruiterUpdateRequest;
import com.hw.hwjobbackend.dto.request.user.UserCreationRequest;
import com.hw.hwjobbackend.dto.response.*;
import com.hw.hwjobbackend.dto.response.user.CandidateResponse;
import com.hw.hwjobbackend.dto.response.user.RecruiterResponse;
import com.hw.hwjobbackend.dto.response.user.UserCreationResponse;
import com.hw.hwjobbackend.dto.response.user.UserResponse;
import com.hw.hwjobbackend.service.user.CandidateService;
import com.hw.hwjobbackend.service.user.RecruiterService;
import com.hw.hwjobbackend.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
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
    ApiResponse<List<UserResponse>> getAllUser(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<UserResponse> response = userService.getAllUser(page - 1, size);
        return ApiResponse.<List<UserResponse>>builder()
                .page(response.getNumber() + 1)
                .totalPages(response.getTotalPages())
                .result(response.getContent())
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
