package com.hw.hwjobbackend.service.candidate.job_post;

import com.hw.hwjobbackend.model.dto.response.job_post.JobPostResponse;
import com.hw.hwjobbackend.model.dto.response.job_post.SaveJobPostResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CandidateJobPostService {

    List<JobPostResponse> getRecommendJobPosts(String userId);

    SaveJobPostResponse saveJobPost(String id);

    Page<JobPostResponse> getSavedJobPosts(int page, int size);

    List<JobPostResponse> getAllSavedJobPosts();

}
