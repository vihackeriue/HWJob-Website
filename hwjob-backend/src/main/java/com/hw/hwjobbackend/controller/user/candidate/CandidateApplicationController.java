package com.hw.hwjobbackend.controller.user.candidate;


import com.hw.hwjobbackend.model.dto.request.application.ApplicationCandidateRequest;

import com.hw.hwjobbackend.model.dto.request.application.ApplicationStatusRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.service.candidate.application.CandidateApplicationService;
import com.hw.hwjobbackend.util.PaginationUtils;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/candidates/applications")
public class CandidateApplicationController {

    CandidateApplicationService candidateApplicationService;

    @PostMapping
    ApiResponse<ApplicationResponse> apply(
            @Valid @RequestBody ApplicationCandidateRequest request
    ) {
        return ApiResponse.<ApplicationResponse>builder()
                .result(candidateApplicationService.applyJob(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<JobPostResponse>> getAppliedJobs(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        if (page != null && size != null) {
            int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);

            Page<JobPostResponse> response = candidateApplicationService
                    .getAllJobPostsApplied(zeroBasedPage, size);

            return ApiResponse.<List<JobPostResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(response.getNumber()))
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }

        return ApiResponse.<List<JobPostResponse>>builder()
                .result(candidateApplicationService.getAllJobPostsApplied())
                .build();
    }
    @PatchMapping("/job-posts/{jobPostId}/status")
    public ApiResponse<Void> updateApplicationStatus(@PathVariable String jobPostId,
                                                     @RequestBody ApplicationStatusRequest request){
        candidateApplicationService.updateApplicationStatus(jobPostId, request.getStatus());
        return ApiResponse.<Void>builder().message("Cập nhật thành công!").build();

    }
}
