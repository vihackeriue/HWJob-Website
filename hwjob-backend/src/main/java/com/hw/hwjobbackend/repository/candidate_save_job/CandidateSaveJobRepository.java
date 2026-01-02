package com.hw.hwjobbackend.repository.candidate_save_job;

import com.hw.hwjobbackend.model.entity.candidate_save_job.CandidateSaveJob;
import com.hw.hwjobbackend.model.entity.candidate_save_job.CandidateSaveJobId;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidateSaveJobRepository extends JpaRepository<CandidateSaveJob, CandidateSaveJobId> {

    @Query("SELECT CASE WHEN COUNT(csj) > 0 THEN true ELSE false END " +
            "FROM CandidateSaveJob csj " +
            "WHERE csj.candidate.id = :candidateId AND csj.jobPost.id = :jobPostId")
    boolean existsByCandidateIdAndJobPostId(
            @Param("candidateId") String candidateId,
            @Param("jobPostId") String jobPostId);


    @Query("""
            SELECT csj.jobPost FROM CandidateSaveJob csj
            WHERE csj.candidate.id = :candidateId
            ORDER BY csj.createdAt DESC
            """)
    Page<JobPost> findSavedJobPostsByCandidateId(
            @Param("candidateId") String candidateId,
            Pageable pageable
    );

    @Query("""
            SELECT csj.jobPost FROM CandidateSaveJob csj
            WHERE csj.candidate.id = :candidateId
            ORDER BY csj.createdAt DESC
            """)
    List<JobPost> findAllSavedJobPostsByCandidateId(
            @Param("candidateId") String candidateId
    );

    long countByJobPostId(String jobPostId);

    @Query("SELECT csj.jobPost.id FROM CandidateSaveJob csj WHERE csj.candidate.id = :candidateId")
    List<String> findAllJobPostIdsByCandidateId(String candidateId);
}
