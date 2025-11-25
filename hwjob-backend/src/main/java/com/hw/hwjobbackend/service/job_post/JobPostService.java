package com.hw.hwjobbackend.service.job_post;

import com.hw.hwjobbackend.model.dto.request.job_post.JobPostCreationRequest;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostDetailResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.SaveJobPostResponse;
import com.hw.hwjobbackend.model.entity.JobPost;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobPostService {

    Page<JobPostResponse> getAllJobPosts(int page, int size);

    List<JobPostResponse> getAllJobPosts();

    JobPostResponse createJobPost(JobPostCreationRequest request);

    JobPost getJobPostEntityById(String id);

    JobPostDetailResponse getJobPostDetail(String id);

    SaveJobPostResponse saveJobPost(String id);

}
