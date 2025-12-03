package com.hw.hwjobbackend.controller.common;


import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.region.RegionResponse;
import com.hw.hwjobbackend.service.shared.region.RegionService;
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
@RequestMapping("/common/regions")
public class RegionController {

    RegionService regionService;

    @GetMapping
    public ApiResponse<List<RegionResponse>> getRegions(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        if (page != null && size != null) {
            int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);

            Page<RegionResponse> response = regionService.getAllRegion(zeroBasedPage, size);
            return ApiResponse.<List<RegionResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(response.getNumber()))
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }
        return ApiResponse.<List<RegionResponse>>builder()
                .result(regionService.getAllRegion())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<RegionResponse> getRegionById(@PathVariable int id) {
        return ApiResponse.<RegionResponse>builder()
                .result(regionService.getRegionById(id))
                .build();
    }

}
