package com.hw.hwjobbackend.controller.admin;


import com.hw.hwjobbackend.model.dto.request.user.UserStatusRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.user.UserResponse;
import com.hw.hwjobbackend.service.admin.admin_user.AdminUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/admin/users")
public class AdminUserController {

    AdminUserService adminUserService;

    @GetMapping
    ApiResponse<List<UserResponse>> getAllUser(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        if (page != null && size != null) {
            Page<UserResponse> response = adminUserService.getUsers(page - 1, size);
            return ApiResponse.<List<UserResponse>>builder()
                    .page(response.getNumber() + 1)
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }
        return ApiResponse.<List<UserResponse>>builder()
                .result(adminUserService.getAllUsers())
                .build();
    }

    @PutMapping("/change-status/{id}")
    ApiResponse<Void> changeUserStatus(@PathVariable String id,
                                       @RequestBody UserStatusRequest request) {
        adminUserService.changeUserStatus(id, request);
        return ApiResponse.<Void>builder().build();
    }
}
