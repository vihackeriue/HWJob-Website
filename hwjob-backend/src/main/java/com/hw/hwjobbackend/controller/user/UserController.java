package com.hw.hwjobbackend.controller.user;


import com.hw.hwjobbackend.model.dto.request.user.UserCreationRequest;
import com.hw.hwjobbackend.model.dto.request.user.UserUpdatePasswordRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
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

    @GetMapping("/me")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserInfo())
                .build();
    }

    @PutMapping("/me/avatar")
    ApiResponse<UpdateAvatarResponse> updateAvatar(
            @RequestParam("file") MultipartFile file) {
        return ApiResponse.<UpdateAvatarResponse>builder()
                .result(userService.updateAvatar(file))
                .build();
    }

    @PutMapping("/me/password")
    ApiResponse<Void> updatePassword(
            @RequestBody UserUpdatePasswordRequest request
    ) {
        userService.updatePassword(request);
        return ApiResponse.<Void>builder()
                .build();
    }
}
