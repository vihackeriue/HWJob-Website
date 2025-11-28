package com.hw.hwjobbackend.controller.candidate;

import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.SaveJobPostResponse;
import com.hw.hwjobbackend.service.candidate.job_post.CandidateJobPostService;
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
@RequestMapping("/candidate/job-posts")
public class CandidateJobPostController {

    CandidateJobPostService candidateJobPostService;

    @PostMapping("/{id}")
    public ApiResponse<SaveJobPostResponse> saveJobPost(@PathVariable String id) {
        return ApiResponse.<SaveJobPostResponse>builder()
                .result(candidateJobPostService.saveJobPost(id))
                .build();
    }

    @GetMapping
    public ApiResponse<List<JobPostResponse>> getSavedJobPosts(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    ) {
        if (page != null && size != null) {
            int zeroBasedPage = PaginationUtils.toZeroBasedPage(page);

            Page<JobPostResponse> response = candidateJobPostService.getSavedJobPosts(
                    zeroBasedPage, size);

            return ApiResponse.<List<JobPostResponse>>builder()
                    .page(PaginationUtils.toOneBasedPage(response.getNumber()))
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }

        return ApiResponse.<List<JobPostResponse>>builder()
                .result(candidateJobPostService.getAllSavedJobPosts())
                .build();
    }
}