package com.hw.hwjobbackend.service.candidate.job_post;

import com.hw.hwjobbackend.model.dto.response.job_post.SaveJobPostResponse;

public interface CandidateJobPostService {
    SaveJobPostResponse saveJobPost(String id);
}
