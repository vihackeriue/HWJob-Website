package com.hw.hwjobbackend.service.candidate.application;

import com.hw.hwjobbackend.model.dto.request.application.ApplicationCandidateRequest;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CandidateApplicationService {

    ApplicationResponse applyJob(ApplicationCandidateRequest request);

    Page<JobPostResponse> getAllJobPostsApplied(int page, int size);

    List<JobPostResponse> getAllJobPostsApplied();

}
