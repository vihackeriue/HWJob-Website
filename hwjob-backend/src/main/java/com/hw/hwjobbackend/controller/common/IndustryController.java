package com.hw.hwjobbackend.controller.common;


import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.industry.IndustryResponse;
import com.hw.hwjobbackend.service.shared.industry.IndustryService;
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
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        if (page != null && size != null) {
            Page<IndustryResponse> response = industryService.getIndustries(page - 1, size);
            return ApiResponse.<List<IndustryResponse>>builder()
                    .page(response.getNumber() + 1)
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }
        return ApiResponse.<List<IndustryResponse>>builder()
                .result(industryService.getAllIndustries())
                .build();

    }

    @GetMapping("/{id}")
    ApiResponse<IndustryResponse> getIndustryById(@PathVariable Long id) {
        return ApiResponse.<IndustryResponse>builder()
                .result(industryService.getIndustryById(id))
                .build();
    }


}
