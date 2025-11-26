package com.hw.hwjobbackend.controller.common;


import com.hw.hwjobbackend.model.dto.request.user.UserCreationRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.user.RecruiterProfileResponse;
import com.hw.hwjobbackend.model.dto.response.user.UpdateAvatarResponse;
import com.hw.hwjobbackend.model.dto.response.user.UserCreationResponse;
import com.hw.hwjobbackend.model.dto.response.user.UserResponse;
import com.hw.hwjobbackend.service.shared.user.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @PostMapping
    public ApiResponse<UserCreationResponse> create(
            @RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserCreationResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserInfo())
                .build();
    }

    @PutMapping("/upload-avatar")
    ApiResponse<UpdateAvatarResponse> updateAvatar(
            @RequestParam("file") MultipartFile file) {
        return ApiResponse.<UpdateAvatarResponse>builder()
                .result(userService.updateAvatar(file))
                .build();
    }

    @GetMapping("/recruiter-profiles/{id}")
    public ApiResponse<RecruiterProfileResponse> getRecruiterProfile(
            @PathVariable String id
    ) {
        return ApiResponse.<RecruiterProfileResponse>builder()
                .result(userService.getRecruiterProfile(id))
                .build();
    }
}
