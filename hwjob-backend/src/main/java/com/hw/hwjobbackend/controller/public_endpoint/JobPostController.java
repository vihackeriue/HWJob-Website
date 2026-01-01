package com.hw.hwjobbackend.controller.public_endpoint;

import com.hw.hwjobbackend.model.dto.request.job_post.JobPostFilterRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.service.shared.job_post.JobPostService;
import com.hw.hwjobbackend.util.PaginationUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/public/job-posts")
public class JobPostController {

    JobPostService jobPostService;

    @GetMapping
    public ApiResponse<List<JobPostResponse>> getAllJobPosts(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
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
            int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);

            Page<JobPostResponse> response = jobPostService.getAllJobPosts(
                    zeroBasedPage, size, filterRequest);

            return ApiResponse.<List<JobPostResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(response.getNumber()))
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }

        return ApiResponse.<List<JobPostResponse>>builder()
                .result(jobPostService.getAllJobPosts(filterRequest))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<JobPostDetailResponse> getJobPostDetail(@PathVariable String id, HttpServletRequest request) {
        return ApiResponse.<JobPostDetailResponse>builder()
                .result(jobPostService.getJobPostDetail(id, request))
                .build();
    }
}
