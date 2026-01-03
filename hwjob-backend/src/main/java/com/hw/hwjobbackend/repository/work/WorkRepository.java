package com.hw.hwjobbackend.repository.work;


import com.hw.hwjobbackend.model.dto.response.job_post.projection.RecruiterWorkSalaryStatsResponse;
import com.hw.hwjobbackend.model.entity.works.Work;
import com.hw.hwjobbackend.model.enums.WorkStatusEnum;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
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

    long countByJobPostId(String jobPostId);

    long countByJobPostIdAndStatus(String jobPostId, WorkStatusEnum status);

    @Query("SELECT SUM(w.agreedSalary) FROM Work w WHERE w.jobPost.id = :jobPostId")
    BigInteger sumAgreedSalaryByJobPost(@Param("jobPostId") String jobPostId);

    @Query("SELECT SUM(w.agreedSalary) FROM Work w " +
            "WHERE w.jobPost.id = :jobPostId AND w.status = :status")
    BigInteger sumAgreedSalaryByJobPostAndStatus(@Param("jobPostId") String jobPostId,
                                               @Param("status") WorkStatusEnum status);

    @Query("""
        SELECT w FROM Work w
        WHERE w.recruiter.id = :recruiterId
        ORDER BY w.createdAt DESC
    """)
    Page<Work> findAllByRecruiterId(
            @Param("recruiterId") String recruiterId,
            Pageable pageable
    );
    @Query("""
        SELECT w FROM Work w
        WHERE w.recruiter.id = :recruiterId
        ORDER BY w.createdAt DESC
    """)
    List<Work> findAllByRecruiterId(
            @Param("recruiterId") String recruiterId
    );


//    Stats Salary
    @Query("""
        SELECT
            COALESCE(SUM(CASE WHEN w.status = 'PAID' THEN w.agreedSalary ELSE 0 END), 0)
                AS paidSalary,
            COALESCE(SUM(CASE WHEN w.status <> 'PAID' THEN w.agreedSalary ELSE 0 END), 0)
                AS unpaidSalary,
            COALESCE(SUM(w.agreedSalary), 0)
                AS totalSalary
        FROM Work w
        WHERE w.recruiter.id = :recruiterId
    """)
    RecruiterWorkSalaryStatsResponse getRecruiterWorkSalaryStats(
            @Param("recruiterId") String recruiterId
    );
    }
