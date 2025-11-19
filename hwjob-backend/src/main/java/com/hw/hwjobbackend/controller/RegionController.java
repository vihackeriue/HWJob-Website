package com.hw.hwjobbackend.controller;


import com.hw.hwjobbackend.dto.response.ApiResponse;
import com.hw.hwjobbackend.dto.response.region.ProvinceResponse;
import com.hw.hwjobbackend.service.region.ProvinceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/regions")
public class RegionController {

    ProvinceService provinceService;

    @GetMapping
    public ApiResponse<List<ProvinceResponse>> getProvinces(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {

        if (page != null && size != null) {
            Page<ProvinceResponse> response = provinceService.getAllProvince(page - 1, size);
            return ApiResponse.<List<ProvinceResponse>>builder()
                    .page(response.getNumber() + 1)
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }
        return ApiResponse.<List<ProvinceResponse>>builder()
                .result(provinceService.getAllProvince())
                .build();
    }

    @GetMapping("/{code}")
    public ApiResponse<ProvinceResponse> getProvinceByCode(@PathVariable int code) {
        return ApiResponse.<ProvinceResponse>builder()
                .result(provinceService.getProvinceByCode(code))
                .build();
    }

}
