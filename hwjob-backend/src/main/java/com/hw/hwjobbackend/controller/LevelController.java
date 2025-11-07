package com.hw.hwjobbackend.controller;


import com.hw.hwjobbackend.dto.request.level.LevelRequest;
import com.hw.hwjobbackend.dto.response.ApiResponse;
import com.hw.hwjobbackend.dto.response.level.LevelResponse;
import com.hw.hwjobbackend.service.level.LevelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/levels")
@Slf4j
public class LevelController {
    LevelService levelService;

    @GetMapping
    public ApiResponse<List<LevelResponse>> getAllLevels() {
        return ApiResponse.<List<LevelResponse>>builder()
                .result(levelService.getAllLevels())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<LevelResponse> getLevelById(@PathVariable Long id) {
        return ApiResponse.<LevelResponse>builder()
                .result(levelService.getLevelById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<LevelResponse> createLevel(@RequestBody LevelRequest request) {
        return ApiResponse.<LevelResponse>builder()
                .result(levelService.createLevel(request))
                .build();
    }


    @PutMapping("/{id}")
    public ApiResponse<LevelResponse> updateLevel(@PathVariable Long id, @RequestBody LevelRequest request) {
        return ApiResponse.<LevelResponse>builder()
                .result(levelService.updateLevel(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLevel(@PathVariable Long id) {
        levelService.deleteLevel(id);
        return ApiResponse.<Void>builder().build();
    }


}
