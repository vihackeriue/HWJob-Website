package com.hw.hwjobbackend.controller.common;


import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.level.LevelResponse;
import com.hw.hwjobbackend.service.shared.level.LevelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/levels")
public class LevelController {

    LevelService levelService;

    @GetMapping
    public ApiResponse<List<LevelResponse>> getAllLevels(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {

        if (page != null && size != null) {
            Page<LevelResponse> response = levelService.getLevels(page - 1, size);
            return ApiResponse.<List<LevelResponse>>builder()
                    .page(response.getNumber() + 1)
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }
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

}
