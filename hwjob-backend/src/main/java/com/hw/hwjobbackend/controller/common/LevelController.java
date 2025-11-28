package com.hw.hwjobbackend.controller.common;

import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.level.LevelResponse;
import com.hw.hwjobbackend.service.shared.level.LevelService;
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
@RequestMapping("/levels")
public class LevelController {

    LevelService levelService;

    @GetMapping
    ApiResponse<List<LevelResponse>> getAllLevels(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        if (page != null && size != null) {
            int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);

            Page<LevelResponse> response = levelService.getLevels(zeroBasedPage, size);

            return ApiResponse.<List<LevelResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(response.getNumber()))
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }

        return ApiResponse.<List<LevelResponse>>builder()
                .result(levelService.getAllLevels())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<LevelResponse> getLevelById(@PathVariable Long id) {
        return ApiResponse.<LevelResponse>builder()
                .result(levelService.getLevelById(id))
                .build();
    }
}