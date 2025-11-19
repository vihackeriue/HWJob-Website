package com.hw.hwjobbackend.repository.candidate_save_job;

import com.hw.hwjobbackend.entity.candidate_save_job.CandidateSaveJob;
import com.hw.hwjobbackend.entity.candidate_save_job.CandidateSaveJobId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateSaveJobRepository extends JpaRepository<CandidateSaveJob, CandidateSaveJobId> {
}
