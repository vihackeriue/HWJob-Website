package com.hw.hwjobbackend.controller;


import com.hw.hwjobbackend.model.dto.request.industry.IndustryRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.industry.IndustryResponse;
import com.hw.hwjobbackend.service.industry.IndustryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/industries")
public class IndustryController {

    IndustryService industryService;

    @GetMapping
    ApiResponse<List<IndustryResponse>> getAllIndustries(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Page<IndustryResponse> response = industryService.getAllIndustryNames(page - 1, size);
        return ApiResponse.<List<IndustryResponse>>builder()
                .page(response.getNumber() + 1)
                .totalPages(response.getTotalPages())
                .result(response.getContent())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<IndustryResponse> getIndustryById(@PathVariable Long id) {
        return ApiResponse.<IndustryResponse>builder()
                .result(industryService.getIndustryById(id))
                .build();
    }

    @PostMapping
    ApiResponse<IndustryResponse> createIndustry(@RequestBody IndustryRequest request) {
        return ApiResponse.<IndustryResponse>builder()
                .result(industryService.createIndustry(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<IndustryResponse> updateIndustry(@PathVariable Long id, @RequestBody IndustryRequest request) {
        return ApiResponse.<IndustryResponse>builder()
                .result(industryService.updateIndustry(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteIndustry(@PathVariable Long id) {
        industryService.deleteIndustry(id);
        return ApiResponse.<Void>builder().build();
    }
}
