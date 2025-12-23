package com.hw.hwjobbackend.controller.user.candidate;

import com.hw.hwjobbackend.model.dto.request.application.ApplicationStatusRequest;
import com.hw.hwjobbackend.model.dto.request.work.UpdateWorkStatusRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.dto.response.work.WorkOverviewResponse;
import com.hw.hwjobbackend.model.dto.response.work.WorkResponse;
import com.hw.hwjobbackend.service.candidate.work.CandidateWorkService;
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
@RequestMapping("/candidates/works")
public class CandidateWorkController {

    CandidateWorkService candidateWorkService;

    @GetMapping("/{jobPostId}/overview")
    public ApiResponse<WorkOverviewResponse> getWorkOverview(
            @PathVariable String jobPostId
    ) {
        return ApiResponse.<WorkOverviewResponse>builder()
                .result(candidateWorkService.getWorkOverviewForCandidate(jobPostId))
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<List<WorkResponse>> getMyWorks(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {

        if (page != null && size != null) {
            int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);

            Page<WorkResponse> response = candidateWorkService
                    .getAllWorksOfCandidate(zeroBasedPage, size);

            return ApiResponse.<List<WorkResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(response.getNumber()))
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }

        return ApiResponse.<List<WorkResponse>>builder()
                .result(candidateWorkService.getAllWorksOfCandidate())
                .build();
    }
    @PatchMapping("/job-posts/{jobPostId}/status")
    public ApiResponse<Void> updateApplicationStatus(@PathVariable String jobPostId,
                                                     @RequestBody UpdateWorkStatusRequest request){
        candidateWorkService.updateApplicationStatus(jobPostId, request);
        return ApiResponse.<Void>builder().message("Cập nhật thành công!").build();

    }
}
