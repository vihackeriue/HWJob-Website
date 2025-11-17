package com.hw.hwjobbackend.service.job_post;

import com.hw.hwjobbackend.dto.request.job_post.JobPostCreationRequest;
import com.hw.hwjobbackend.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.entity.JobPost;
import org.springframework.data.domain.Page;

import java.util.List;

public interface JobPostService {

    Page<JobPostResponse> getAllJobPosts(int page, int size);

    List<JobPostResponse> getAllJobPosts();

    JobPostResponse createJobPost(JobPostCreationRequest request);

    JobPost getJobPostEntityById(String id);

}
