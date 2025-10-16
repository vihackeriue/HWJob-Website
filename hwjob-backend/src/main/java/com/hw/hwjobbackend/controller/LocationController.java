package com.hw.hwjobbackend.controller;


import com.hw.hwjobbackend.dto.response.ApiResponse;
import com.hw.hwjobbackend.dto.response.ProvinceResponse;
import com.hw.hwjobbackend.service.ProvinceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/location")
public class LocationController {

    ProvinceService provinceService;

    @GetMapping
    public ApiResponse<List<ProvinceResponse>> getProvinces() {
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
