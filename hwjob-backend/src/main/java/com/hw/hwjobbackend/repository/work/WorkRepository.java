package com.hw.hwjobbackend.repository.work;


import com.hw.hwjobbackend.model.entity.works.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkRepository extends JpaRepository<Work, String> {
    boolean existsByCandidateIdAndJobPostId(String candidateId, String jobPostId);
    Optional<Work> findByCandidateIdAndJobPostId(
            String candidateId,
            String jobPostId
    );
    void deleteByJobPostIdAndCandidateId(String jobPostId, String candidateId);

    List<Work> findAllByCandidateIdOrderByCreatedAtDesc(String candidateId);

    Page<Work> findByCandidateIdOrderByCreatedAtDesc(
            String candidateId,
            Pageable pageable
    );

    Optional<Work> findByJobPostIdAndCandidateIdAndRecruiterId(String jobPostId, String candidateId, String recruiterId);

    Page<Work> findByJobPostIdAndRecruiterIdOrderByCreatedAtDesc(String jobPostId, String recruiterId, Pageable pageable);

    List<Work> findByJobPostIdAndRecruiterIdOrderByCreatedAtDesc(String jobPostId, String recruiterId);
}
