package com.hw.hwjobbackend.repository.job_post;

import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.enums.JobPostStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, String> {


    @Query("""
             SELECT jp FROM JobPost jp
             WHERE jp.status = :status
               AND (jp.endedTime IS NULL OR jp.endedTime > CURRENT_TIMESTAMP)
               AND (:industryId IS NULL OR jp.industry.id = :industryId)
               AND (:levelId IS NULL OR jp.level.id = :levelId)
               AND (:jobTypeId IS NULL OR jp.jobType.id = :jobTypeId)
               AND (:regionId IS NULL OR jp.region.id = :regionId)
             ORDER BY jp.createdAt DESC
            """)
    Page<JobPost> getJobPosts(
            @Param("status") JobPostStatusEnum status,
            @Param("industryId") Long industryId,
            @Param("levelId") Long levelId,
            @Param("jobTypeId") Long jobTypeId,
            @Param("regionId") Integer regionId,
            Pageable pageable
    );

    @Query("""
             SELECT jp FROM JobPost jp
             WHERE jp.status = :status
               AND (jp.endedTime IS NULL OR jp.endedTime > CURRENT_TIMESTAMP)
               AND (:industryId IS NULL OR jp.industry.id = :industryId)
               AND (:levelId IS NULL OR jp.level.id = :levelId)
               AND (:jobTypeId IS NULL OR jp.jobType.id = :jobTypeId)
               AND (:regionId IS NULL OR jp.region.id = :regionId)
             ORDER BY jp.createdAt DESC
            """)
    List<JobPost> getAllJobPosts(
            @Param("status") JobPostStatusEnum status,
            @Param("industryId") Long industryId,
            @Param("levelId") Long levelId,
            @Param("jobTypeId") Long jobTypeId,
            @Param("regionId") Integer regionId
    );


    Page<JobPost> findAllByRecruiterIdOrderByCreatedAtDesc(
            String recruiterId,
            Pageable pageable
    );

    List<JobPost> findAllByRecruiterIdOrderByCreatedAtDesc(String recruiterId);

    Optional<JobPost> findByIdAndRecruiterId(String jobPostId, String recruiterId);

    @Query("""
            SELECT jp FROM JobPost jp
            LEFT JOIN FETCH jp.recruiter r
            WHERE jp.id = :jobPostId
              AND (
                jp.status = 'PUBLIC'
                OR :role = 'ADMIN'
                OR (:role = 'RECRUITER' AND r.id = :userId)
              )
            """)
    Optional<JobPost> findJobPostWithPermission(
            @Param("jobPostId") String jobPostId,
            @Param("userId") String userId,
            @Param("role") String role
    );

    @Query("""
                SELECT CASE WHEN COUNT(j) > 0 THEN true ELSE false END
                FROM JobPost j
                WHERE j.id = :jobPostId
                  AND j.status = 'PUBLIC'
                  AND (j.endedTime IS NULL OR j.endedTime > CURRENT_TIMESTAMP)
            """)
    boolean existsValidJobPost(@Param("jobPostId") String jobPostId);

}