package com.hw.hwjobbackend.controller.admin;

import com.hw.hwjobbackend.model.dto.request.job_type.JobTypeRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.job_type.JobTypeResponse;
import com.hw.hwjobbackend.service.admin.job_type.AdminJobTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/admin/job-types")
public class AdminJobTypeController {

    AdminJobTypeService adminJobTypeService;

    @PostMapping
    ApiResponse<JobTypeResponse> createJobType(@RequestBody JobTypeRequest request) {
        return ApiResponse.<JobTypeResponse>builder()
                .result(adminJobTypeService.createJobType(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<JobTypeResponse> updateJobType(@PathVariable Long id, @RequestBody JobTypeRequest request) {
        return ApiResponse.<JobTypeResponse>builder()
                .result(adminJobTypeService.updateJobType(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteJobType(@PathVariable Long id) {
        adminJobTypeService.deleteJobType(id);
        return ApiResponse.<Void>builder().build();
    }

}
