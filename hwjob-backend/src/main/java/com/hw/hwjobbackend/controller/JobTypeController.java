package com.hw.hwjobbackend.controller;


import com.hw.hwjobbackend.model.dto.request.job_type.JobTypeRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.job_type.JobTypeResponse;
import com.hw.hwjobbackend.service.job_type.JobTypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/job-types")
public class JobTypeController {

    JobTypeService jobTypeService;

    @GetMapping
    ApiResponse<List<JobTypeResponse>> getAllJobTypes(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        if (page != null && size != null) {
            Page<JobTypeResponse> response = jobTypeService.getJobTypes(page - 1, size);
            return ApiResponse.<List<JobTypeResponse>>builder()
                    .page(response.getNumber() + 1)
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }
        return ApiResponse.<List<JobTypeResponse>>builder()
                .result(jobTypeService.getAllJobTypes())
                .build();

    }

    @GetMapping("/{id}")
    ApiResponse<JobTypeResponse> getJobTypeById(@PathVariable Long id) {
        return ApiResponse.<JobTypeResponse>builder()
                .result(jobTypeService.getJobTypeById(id))
                .build();
    }

    @PostMapping
    ApiResponse<JobTypeResponse> createJobType(@RequestBody JobTypeRequest request) {
        return ApiResponse.<JobTypeResponse>builder()
                .result(jobTypeService.createJobType(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<JobTypeResponse> updateJobType(@PathVariable Long id, @RequestBody JobTypeRequest request) {
        return ApiResponse.<JobTypeResponse>builder()
                .result(jobTypeService.updateJobType(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteJobType(@PathVariable Long id) {
        jobTypeService.deleteJobType(id);
        return ApiResponse.<Void>builder().build();
    }
}
