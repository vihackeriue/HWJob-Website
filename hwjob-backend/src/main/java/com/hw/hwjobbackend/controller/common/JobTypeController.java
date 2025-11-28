package com.hw.hwjobbackend.controller.common;

import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.job_type.JobTypeResponse;
import com.hw.hwjobbackend.service.shared.job_type.JobTypeService;
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
@RequestMapping("/job-types")
public class JobTypeController {

    JobTypeService jobTypeService;

    @GetMapping
    ApiResponse<List<JobTypeResponse>> getAllJobTypes(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        if (page != null && size != null) {
            int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);

            Page<JobTypeResponse> response = jobTypeService.getJobTypes(zeroBasedPage, size);

            return ApiResponse.<List<JobTypeResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(response.getNumber()))
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
}