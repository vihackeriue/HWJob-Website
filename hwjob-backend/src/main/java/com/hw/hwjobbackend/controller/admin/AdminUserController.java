package com.hw.hwjobbackend.controller.admin;

import com.hw.hwjobbackend.model.dto.request.user.UserStatusRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.user.UserResponse;
import com.hw.hwjobbackend.service.admin.admin_user.AdminUserService;
import com.hw.hwjobbackend.util.PaginationUtils;
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
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        if (page != null && size != null) {
            int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);

            Page<UserResponse> response = adminUserService.getAllUsers(zeroBasedPage, size);

            return ApiResponse.<List<UserResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(response.getNumber()))
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }

        return ApiResponse.<List<UserResponse>>builder()
                .result(adminUserService.getAllUsers())
                .build();
    }

    @PatchMapping("/{userId}/status")
    ApiResponse<Void> changeUserStatus(
            @PathVariable String userId,
            @RequestBody UserStatusRequest request
    ) {
        adminUserService.changeUserStatus(userId, request);
        return ApiResponse.<Void>builder().build();
    }
}