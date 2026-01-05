package com.hw.hwjobbackend.service.recruiter.job_post;

import com.hw.hwjobbackend.model.dto.request.job_post.BoostJobPostRequest;
import com.hw.hwjobbackend.model.dto.request.job_post.JobPostRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailStatsResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.RecruiterJobPostStatsResponse;
import com.hw.hwjobbackend.model.enums.JobPostStatusEnum;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RecruiterJobPostService {
    JobPostResponse createJobPost(JobPostRequest request);
    void boostJobPost(String jobPostId, BoostJobPostRequest request);
    Page<JobPostResponse> getPostedJobPosts(int page, int size, JobPostStatusEnum status, String keyword);

    List<JobPostResponse> getAllPostedJobPosts(JobPostStatusEnum status, String keyword);

    JobPostDetailResponse editJobPost(String id, JobPostRequest request);

    JobPostDetailStatsResponse getJobPostDetailStats(String jobPostId);

    RecruiterJobPostStatsResponse getRecruiterJobPostStats();


}
