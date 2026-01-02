package com.hw.hwjobbackend.controller.user.recruiter;

import com.hw.hwjobbackend.model.dto.request.application.ApplicationStatusRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationAllCandidateResponse;
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
                .result(recruiterJobPostService.getCandidateApplications(jobPostId))
                .build();
    }

    @GetMapping("/job-posts/{jobPostId}/candidates/ranked")
    public ApiResponse<List<ApplicationCandidateResponse>> getRankedCandidatesAppliedJob(
            @PathVariable String jobPostId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);
        int pageSize = (size != null) ? size : 10;

        Page<ApplicationCandidateResponse> response = recruiterJobPostService
                .getRankedCandidateApplication(zeroBasedPage, pageSize, jobPostId);

        return ApiResponse.<List<ApplicationCandidateResponse>>builder()
                .page(PaginationUtils.toOneBasedPage(response.getNumber()))
                .totalPages(response.getTotalPages())
                .result(response.getContent())
                .build();
    }

    @GetMapping("/candidates/all")
    public ApiResponse<List<ApplicationAllCandidateResponse>> getAllCandidateApplications(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        if (page != null && size != null) {
            Page<ApplicationAllCandidateResponse> response =
                    recruiterJobPostService.getAllCandidateApplicationsOfRecruiter(
                            PaginationUtils.toZeroBasedPage(page),
                            size
                    );

            return ApiResponse.<List<ApplicationAllCandidateResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(response.getNumber()))
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }

        return ApiResponse.<List<ApplicationAllCandidateResponse>>builder()
                .result(
                        recruiterJobPostService.getAllCandidateApplicationsOfRecruiter()
                ).build();
    }

    @PatchMapping("/job-posts/{jobPostId}/candidates/{candidateId}/status")
    public ApiResponse<Void> updateCandidateApplicationStatus(
            @PathVariable String jobPostId,
            @PathVariable String candidateId,
            @RequestBody ApplicationStatusRequest request
    ) {
        recruiterJobPostService.updateCandidateApplicationStatus(jobPostId, candidateId, request);
        return ApiResponse.<Void>builder().message("Cập nhật thành công!").build();
    }
}