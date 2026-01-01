package com.hw.hwjobbackend.controller.user.recruiter;

import com.hw.hwjobbackend.model.dto.request.work.UpdateWorkStatusRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.work.AllWorkCandidateOfRecruiterResponse;
import com.hw.hwjobbackend.model.dto.response.work.WorkCandidateResponse;
import com.hw.hwjobbackend.service.recruiter.work.RecruiterWorkService;
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
@RequestMapping("/recruiters/works")
public class RecruiterWorkController {
    RecruiterWorkService recruiterWorkService;

    @PatchMapping("/job-posts/{jobPostId}/candidates/{candidateId}/status")
    public ApiResponse<Void> updateCandidateApplicationStatus(
            @PathVariable String jobPostId,
            @PathVariable String candidateId,
            @RequestBody UpdateWorkStatusRequest request
    ) {
        recruiterWorkService.updateCandidateWorkStatus(jobPostId, candidateId, request);
        return ApiResponse.<Void>builder().message("Cập nhật thành công!").build();
    }

    @GetMapping("/job-posts/{jobPostId}/candidates")
    public ApiResponse<List<WorkCandidateResponse>> getStaffOfWork(
            @PathVariable String jobPostId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        if (page != null && size != null) {
            int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);

            Page<WorkCandidateResponse> response = recruiterWorkService
                    .getCandidateWork(zeroBasedPage, size, jobPostId);

            return ApiResponse.<List<WorkCandidateResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(response.getNumber()))
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }

        return ApiResponse.<List<WorkCandidateResponse>>builder()
                .result(recruiterWorkService.getCandidateWork(jobPostId))
                .build();
    }
    @GetMapping("/candidates/all")
    public ApiResponse<List<AllWorkCandidateOfRecruiterResponse>> getAllCandidateWork(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        if (page != null && size != null) {
            Page<AllWorkCandidateOfRecruiterResponse> response =
                    recruiterWorkService.getAllCandidateWorkOfRecruiter(
                            PaginationUtils.toZeroBasedPage(page),
                            size
                    );

            return ApiResponse.<List<AllWorkCandidateOfRecruiterResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(response.getNumber()))
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }

        return ApiResponse.<List<AllWorkCandidateOfRecruiterResponse>>builder()
                .result(
                        recruiterWorkService.getAllCandidateWorkOfRecruiter()
                )
                .build();
    }
}
