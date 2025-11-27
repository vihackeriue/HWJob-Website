package com.hw.hwjobbackend.controller.common;

import com.hw.hwjobbackend.model.dto.request.job_post.JobPostFilterRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.service.shared.job_post.JobPostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/job-posts")
public class JobPostController {

    JobPostService jobPostService;

    @GetMapping
    public ApiResponse<List<JobPostResponse>> getJobPosts(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "industryId", required = false) Long industryId,
            @RequestParam(value = "levelId", required = false) Long levelId,
            @RequestParam(value = "jobTypeId", required = false) Long jobTypeId,
            @RequestParam(value = "regionId", required = false) Integer regionId
    ) {
        JobPostFilterRequest filterRequest = JobPostFilterRequest.builder()
                .industryId(industryId)
                .levelId(levelId)
                .jobTypeId(jobTypeId)
                .regionId(regionId)
                .build();
        if (page != null && size != null) {
            Page<JobPostResponse> response = jobPostService.getJobPosts(page - 1, size, filterRequest);
            return ApiResponse.<List<JobPostResponse>>builder()
                    .page(response.getNumber() + 1)
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }
        return ApiResponse.<List<JobPostResponse>>builder()
                .result(jobPostService.getAllJobPosts(filterRequest))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<JobPostDetailResponse> getJobPostDetail(@PathVariable String id) {
        return ApiResponse.<JobPostDetailResponse>builder()
                .result(jobPostService.getJobPostDetail(id))
                .build();
    }
}
