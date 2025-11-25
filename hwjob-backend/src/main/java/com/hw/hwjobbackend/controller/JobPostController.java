package com.hw.hwjobbackend.controller;


import com.hw.hwjobbackend.model.dto.request.job_post.JobPostCreationRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.SaveJobPostResponse;
import com.hw.hwjobbackend.service.job_post.JobPostService;
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
    public ApiResponse<List<JobPostResponse>> getAllJobPosts(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "industryId", required = false) Long industryId

            ) {
        if (page != null && size != null) {
            Page<JobPostResponse> response = jobPostService.getAllJobPosts(page - 1, size);
            return ApiResponse.<List<JobPostResponse>>builder()
                    .page(response.getNumber() + 1)
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }
        return ApiResponse.<List<JobPostResponse>>builder()
                .result(jobPostService.getAllJobPosts())
                .build();
    }

    @PostMapping
    public ApiResponse<JobPostResponse> createJobPost(
            @RequestBody JobPostCreationRequest request) {
        return ApiResponse.<JobPostResponse>builder()
                .result(jobPostService.createJobPost(request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<JobPostDetailResponse> getJobPostById(@PathVariable String id) {
        return ApiResponse.<JobPostDetailResponse>builder()
                .result(jobPostService.getJobPostDetail(id))
                .build();
    }

    @PostMapping("/saved-posts/{id}")
    public ApiResponse<SaveJobPostResponse> saveJobPost(@PathVariable String id) {
        return ApiResponse.<SaveJobPostResponse>builder()
                .result(jobPostService.saveJobPost(id))
                .build();
    }


}
