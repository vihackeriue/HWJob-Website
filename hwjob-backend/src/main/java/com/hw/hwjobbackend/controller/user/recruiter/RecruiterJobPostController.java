package com.hw.hwjobbackend.controller.user.recruiter;


import com.hw.hwjobbackend.model.dto.request.job_post.JobPostRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailStatsResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.RecruiterJobPostStatsResponse;
import com.hw.hwjobbackend.model.enums.JobPostStatusEnum;
import com.hw.hwjobbackend.service.recruiter.job_post.RecruiterJobPostService;
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
@RequestMapping("/recruiters/job-posts")
public class RecruiterJobPostController {

    RecruiterJobPostService recruiterJobPostService;

    @PostMapping
    public ApiResponse<JobPostResponse> createJobPost(
            @RequestBody JobPostRequest request) {
        return ApiResponse.<JobPostResponse>builder()
                .result(recruiterJobPostService.createJobPost(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<JobPostResponse>> getPostedJobPosts(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(required = false) JobPostStatusEnum status,
            @RequestParam(required = false) String keyword
    ) {
        if (page != null && size != null) {

            int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);

            Page<JobPostResponse> response = recruiterJobPostService
                    .getPostedJobPosts(zeroBasedPage, size, status, keyword);
            return ApiResponse.<List<JobPostResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(response.getNumber()))
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }
        return ApiResponse.<List<JobPostResponse>>builder()
                .result(recruiterJobPostService.getAllPostedJobPosts(status, keyword))
                .build();
    }

    @GetMapping("/stats")
    public ApiResponse<RecruiterJobPostStatsResponse> getRecruiterJobPostStats(

    ) {
        return ApiResponse.<RecruiterJobPostStatsResponse>builder().result(recruiterJobPostService
                .getRecruiterJobPostStats()).build();
    }

    @GetMapping("/{jobPostId}/stats")
    public ApiResponse<JobPostDetailStatsResponse> getJobPostStats(
            @PathVariable String jobPostId
    ) {
        JobPostDetailStatsResponse stats = recruiterJobPostService.getJobPostDetailStats(jobPostId);
        return ApiResponse.<JobPostDetailStatsResponse>builder()
                .result(stats)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<JobPostDetailResponse> updateJobPost(
            @PathVariable String id,
            @RequestBody JobPostRequest request
    ) {
        return ApiResponse.<JobPostDetailResponse>builder()
                .result(recruiterJobPostService.editJobPost(id, request))
                .build();
    }
}
