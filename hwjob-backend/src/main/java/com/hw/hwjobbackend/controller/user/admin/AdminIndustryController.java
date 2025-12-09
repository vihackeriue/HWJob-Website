package com.hw.hwjobbackend.controller.user.admin;


import com.hw.hwjobbackend.model.dto.request.industry.IndustryRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.industry.IndustryResponse;
import com.hw.hwjobbackend.service.admin.industry.AdminIndustryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/admin/industries")
public class AdminIndustryController {

    AdminIndustryService adminIndustryService;

    @PostMapping
    ApiResponse<IndustryResponse> createIndustry(@RequestBody IndustryRequest request) {
        return ApiResponse.<IndustryResponse>builder()
                .result(adminIndustryService.createIndustry(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<IndustryResponse> updateIndustry(@PathVariable Long id, @RequestBody IndustryRequest request) {
        return ApiResponse.<IndustryResponse>builder()
                .result(adminIndustryService.updateIndustry(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteIndustry(@PathVariable Long id) {
        adminIndustryService.deleteIndustry(id);
        return ApiResponse.<Void>builder().build();
    }

}
