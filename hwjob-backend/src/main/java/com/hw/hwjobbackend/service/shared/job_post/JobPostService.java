package com.hw.hwjobbackend.service.shared.job_post;

import com.hw.hwjobbackend.model.dto.request.job_post.JobPostFilterRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobPostService {
    Page<JobPostResponse> getAllJobPosts(Integer page, Integer size, JobPostFilterRequest filter);

    List<JobPostResponse> getAllJobPosts(JobPostFilterRequest filter);

    JobPostDetailResponse getJobPostDetail(String id);

}
