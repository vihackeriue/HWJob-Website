package com.hw.hwjobbackend.controller.user.admin;

import com.hw.hwjobbackend.model.dto.request.level.LevelRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.level.LevelResponse;
import com.hw.hwjobbackend.service.admin.level.AdminLevelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/admin/levels")
public class AdminLevelController {

    AdminLevelService adminLevelService;

    @PostMapping
    public ApiResponse<LevelResponse> createLevel(@RequestBody LevelRequest request) {
        return ApiResponse.<LevelResponse>builder()
                .result(adminLevelService.createLevel(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<LevelResponse> updateLevel(@PathVariable Long id, @RequestBody LevelRequest request) {
        return ApiResponse.<LevelResponse>builder()
                .result(adminLevelService.updateLevel(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLevel(@PathVariable Long id) {
        adminLevelService.deleteLevel(id);
        return ApiResponse.<Void>builder().build();
    }

}
