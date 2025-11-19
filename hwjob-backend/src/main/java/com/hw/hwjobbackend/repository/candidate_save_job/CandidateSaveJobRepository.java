package com.hw.hwjobbackend.repository.candidate_save_job;

import com.hw.hwjobbackend.entity.JobPost;
import com.hw.hwjobbackend.entity.candidate_save_job.CandidateSaveJob;
import com.hw.hwjobbackend.entity.candidate_save_job.CandidateSaveJobId;
import com.hw.hwjobbackend.entity.user.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateSaveJobRepository extends JpaRepository<CandidateSaveJob, CandidateSaveJobId> {
    boolean existsCandidateSaveJobByCandidateAndJobPost(Candidate candidate, JobPost jobPost);
}
