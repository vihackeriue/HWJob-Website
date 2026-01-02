package com.hw.hwjobbackend.service.shared.indexing;


import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.user.Candidate;

public interface IndexingService {

    void createCandidateIndexing(Candidate candidate);

    void createJobPostIndexing(JobPost jobPost);

    void indexAllCandidates();

    void indexAllJobPosts();

}
