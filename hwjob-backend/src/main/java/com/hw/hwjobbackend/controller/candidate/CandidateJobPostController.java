package com.hw.hwjobbackend.controller.candidate;


import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.SaveJobPostResponse;
import com.hw.hwjobbackend.service.candidate.job_post.CandidateJobPostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/candidate/job-posts")
public class CandidateJobPostController {
    CandidateJobPostService candidateJobPostService;

    @PostMapping("/saved-posts/{id}")
    public ApiResponse<SaveJobPostResponse> saveJobPost(@PathVariable String id) {
        return ApiResponse.<SaveJobPostResponse>builder()
                .result(candidateJobPostService.saveJobPost(id))
                .build();
    }


}
