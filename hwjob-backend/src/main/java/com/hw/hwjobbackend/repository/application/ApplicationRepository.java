package com.hw.hwjobbackend.repository.application;

import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, ApplicationId> {

    // ===== Recruiter methods =====


    @Query("SELECT a.candidate.id FROM Application a " +
            "WHERE a.jobPost.id = :jobPostId " +
            "AND a.jobPost.recruiter.id =:recruiterId AND a.status = 'PENDING' " +
            "ORDER BY a.createdAt DESC")
    List<String> findAllPendingCandidateIdAndJobPostIdAndRecruiterByCreatedAtDesc(
            @Param("jobPostId") String jobPostId,
            @Param("recruiterId") String recruiterId
    );

    @Query("SELECT a FROM Application a " +
            "WHERE a.jobPost.id = :jobPostId " +
            "AND a.jobPost.recruiter.id = :recruiterId " +
            "ORDER BY a.createdAt DESC")
    Page<Application> findByJobPostIdAndRecruiterIdOrderByCreatedAtDesc(
            @Param("jobPostId") String jobPostId,
            @Param("recruiterId") String recruiterId,
            Pageable pageable
    );

    @Query("SELECT a FROM Application a " +
            "WHERE a.jobPost.id = :jobPostId " +
            "AND a.jobPost.recruiter.id = :recruiterId " +
            "ORDER BY a.createdAt DESC")
    List<Application> findAllByJobPostIdAndRecruiterIdOrderByCreatedAtDesc(
            @Param("jobPostId") String jobPostId,
            @Param("recruiterId") String recruiterId
    );

    @Query("SELECT a FROM Application a " +
            "JOIN FETCH a.jobPost jp " +
            "WHERE a.id = :applicationId " +
            "AND jp.recruiter.id = :recruiterId")
    Optional<Application> findByIdAndRecruiterId(
            @Param("applicationId") ApplicationId applicationId,
            @Param("recruiterId") String recruiterId
    );


    @Query("SELECT a FROM Application a " +
            "JOIN FETCH a.jobPost jp " +
            "WHERE a.candidate.id = :candidateId " +
            "ORDER BY a.createdAt DESC")
    Page<Application> findByCandidateIdOrderByCreatedAtDesc(
            @Param("candidateId") String candidateId,
            Pageable pageable
    );

    @Query("SELECT a FROM Application a " +
            "JOIN FETCH a.jobPost jp " +
            "WHERE a.candidate.id = :candidateId " +
            "ORDER BY a.createdAt DESC")
    List<Application> findAllByCandidateIdOrderByCreatedAtDesc(
            @Param("candidateId") String candidateId
    );

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM Application a " +
            "WHERE a.candidate.id = :candidateId AND a.jobPost.id = :jobPostId")
    boolean existsByCandidateIdAndJobPostId(
            @Param("candidateId") String candidateId,
            @Param("jobPostId") String jobPostId);

    @Query("""
                SELECT a FROM Application a
                WHERE a.jobPost.id = :jobPostId
                  AND a.candidate.id = :candidateId
            """)
    Optional<Application> findByJobPostIdAndCandidateId(
            @Param("jobPostId") String jobPostId,
            @Param("candidateId") String candidateId
    );

}