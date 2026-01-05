package com.hw.hwjobbackend.service.shared.job_post;

import com.hw.hwjobbackend.model.dto.request.job_post.JobPostFilterRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import jakarta.servlet.http.HttpServletRequest;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobPostService {
    Page<JobPostResponse> getAllJobPosts(Integer page, Integer size, JobPostFilterRequest filter);

    List<JobPostResponse> getAllJobPosts(JobPostFilterRequest filter);
   List<JobPostResponse> getTop12BoostedJobPosts();
    JobPostDetailResponse getJobPostDetail(String id, HttpServletRequest request);

    void increaseViewCount(String jobPostId, Long viewCount);

    Page<JobPostResponse> getAllJobPostsByRecruiterId(Integer page, Integer size, String recruiterId);

}
