package com.hw.hwjobbackend.controller.recruiter;


import com.hw.hwjobbackend.model.dto.request.job_post.JobPostCreationRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.service.recruiter.job_post.RecruiterJobPostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/recruiter/job-posts")
public class RecruiterJobPostController {

    RecruiterJobPostService recruiterJobPostService;

    @PostMapping
    public ApiResponse<JobPostResponse> createJobPost(
            @RequestBody JobPostCreationRequest request) {
        return ApiResponse.<JobPostResponse>builder()
                .result(recruiterJobPostService.createJobPost(request))
                .build();
    }


}
