package com.hw.hwjobbackend.service.recruiter.job_post;

import com.hw.hwjobbackend.model.dto.request.job_post.JobPostRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecruiterJobPostService {
    JobPostResponse createJobPost(JobPostRequest request);

    Page<JobPostResponse> getPostedJobPosts(int page, int size);

    List<JobPostResponse> getAllPostedJobPosts();

    JobPostDetailResponse editJobPost(String id, JobPostRequest request);

}
