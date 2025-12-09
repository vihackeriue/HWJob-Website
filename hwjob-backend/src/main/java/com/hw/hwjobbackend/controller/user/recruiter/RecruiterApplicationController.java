package com.hw.hwjobbackend.controller.user.recruiter;

import com.hw.hwjobbackend.model.dto.request.application.ApplicationStatusRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationCandidateResponse;
import com.hw.hwjobbackend.service.recruiter.application.RecruiterApplicationService;
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
@RequestMapping("/recruiters/applications")
public class RecruiterApplicationController {

    RecruiterApplicationService recruiterJobPostService;

    @GetMapping("/job-posts/{jobPostId}/candidates")
    public ApiResponse<List<ApplicationCandidateResponse>> getCandidatesAppliedJob(
            @PathVariable String jobPostId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        if (page != null && size != null) {
            int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);

            Page<ApplicationCandidateResponse> response = recruiterJobPostService
                    .getCandidateApplications(zeroBasedPage, size, jobPostId);

            return ApiResponse.<List<ApplicationCandidateResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(response.getNumber()))
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }

        return ApiResponse.<List<ApplicationCandidateResponse>>builder()
                .result(recruiterJobPostService.getAllCandidateApplications(jobPostId))
                .build();
    }

    @PatchMapping("/job-posts/{jobPostId}/candidates/{candidateId}/status")
    public ApiResponse<Void> updateCandidateApplicationStatus(
            @PathVariable String jobPostId,
            @PathVariable String candidateId,
            @RequestBody ApplicationStatusRequest request
    ) {
        recruiterJobPostService.updateCandidateApplicationStatus(jobPostId, candidateId, request);
        return ApiResponse.<Void>builder().build();
    }
}