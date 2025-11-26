package com.hw.hwjobbackend.service.recruiter.job_post;

import com.hw.hwjobbackend.model.dto.request.job_post.JobPostCreationRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;

public interface RecruiterJobPostService {
    JobPostResponse createJobPost(JobPostCreationRequest request);
}
