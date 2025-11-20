package com.hw.hwjobbackend.controller;

import com.hw.hwjobbackend.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.dto.request.user.RecruiterUpdateRequest;
import com.hw.hwjobbackend.dto.request.user.UserCreationRequest;
import com.hw.hwjobbackend.dto.request.user.UserStatusRequest;
import com.hw.hwjobbackend.dto.response.*;
import com.hw.hwjobbackend.dto.response.user.*;
import com.hw.hwjobbackend.service.user.CandidateService;
import com.hw.hwjobbackend.service.user.RecruiterService;
import com.hw.hwjobbackend.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PutMapping("/candidates")
    ApiResponse<CandidateResponse> updateCandidate(
            @RequestBody @Valid CandidateUpdateRequest request) {
        return ApiResponse.<CandidateResponse>builder()
                .result(candidateService.updateCandidateInfo(request))
                .build();
    }

    @PutMapping("/recruiters")
    ApiResponse<RecruiterResponse> updateRecruiter(
            @RequestBody @Valid RecruiterUpdateRequest request
    ) {
        return ApiResponse.<RecruiterResponse>builder()
                .result(recruiterService.updateRecruiterInfo(request))
                .build();
    }

    @PutMapping("/change-status/{id}")
    ApiResponse<Void> changeUserStatus(@PathVariable String id,
                                       @RequestBody UserStatusRequest request) {
        userService.changeUserStatus(id, request);
        return ApiResponse.<Void>builder().build();
    }

    @PutMapping("/upload-avatar")
    ApiResponse<UpdateAvatarResponse> updateAvatar(
            @RequestParam("file") MultipartFile file) {
        return ApiResponse.<UpdateAvatarResponse>builder()
                .result(userService.updateAvatar(file))
                .build();
    }
}
