package com.hw.hwjobbackend.repository.job_post;

import com.hw.hwjobbackend.model.dto.response.job_post.projection.RecruiterPostingFrequencyResponse;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.enums.JobPostStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    ORDER BY
      CASE
        WHEN jp.isBoosted = true
         AND jp.boostExpiredAt IS NOT NULL
         AND jp.boostExpiredAt > CURRENT_TIMESTAMP
        THEN jp.boostPriority
        ELSE 0
      END DESC,
      jp.createdAt DESC
""")
    Page<JobPost> getAllJobPosts(
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
    ORDER BY
      CASE
        WHEN jp.isBoosted = true
         AND jp.boostExpiredAt IS NOT NULL
         AND jp.boostExpiredAt > CURRENT_TIMESTAMP
        THEN jp.boostPriority
        ELSE 0
      END DESC,
      jp.createdAt DESC
""")
    List<JobPost> getAllJobPosts(
            @Param("status") JobPostStatusEnum status,
            @Param("industryId") Long industryId,
            @Param("levelId") Long levelId,
            @Param("jobTypeId") Long jobTypeId,
            @Param("regionId") Integer regionId
    );

    @Query("""
    SELECT jp FROM JobPost jp
    WHERE jp.status = :status
      AND jp.isBoosted = TRUE
      AND jp.boostExpiredAt IS NOT NULL
      AND jp.boostExpiredAt > CURRENT_TIMESTAMP
      AND (jp.endedTime IS NULL OR jp.endedTime > CURRENT_TIMESTAMP)
    ORDER BY
      jp.boostPriority DESC,
      jp.createdAt DESC
""")
    List<JobPost> findTop12BoostedJobPosts(
            @Param("status") JobPostStatusEnum status
    );

    @Query("SELECT j FROM JobPost j WHERE j.recruiter.id = :recruiterId " +
            "AND (:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (" +
            "(:status IS NULL) OR " +
            "(:status = 'PUBLIC' AND j.status = 'PUBLIC' AND j.endedTime > CURRENT_TIMESTAMP) OR " +
            "(:status = 'PRIVATE' AND (j.status = 'PRIVATE' OR (j.status = 'PUBLIC' AND j.endedTime <= CURRENT_TIMESTAMP)))" +
            ") ORDER BY j.createdAt DESC")
    Page<JobPost> findByRecruiterAndStatusCustom(
            String recruiterId,
            String status, // Dùng String ở đây để so sánh cho gọn
            String title,
            Pageable pageable
    );

    @Query("SELECT j FROM JobPost j WHERE j.recruiter.id = :recruiterId " +
            "AND (:title IS NULL OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (" +
            "(:status IS NULL) OR " +
            "(:status = 'PUBLIC' AND j.status = 'PUBLIC' AND j.endedTime > CURRENT_TIMESTAMP) OR " +
            "(:status = 'PRIVATE' AND (j.status = 'PRIVATE' OR (j.status = 'PUBLIC' AND j.endedTime <= CURRENT_TIMESTAMP)))" +
            ") ORDER BY j.createdAt DESC")
    List<JobPost> findAllByRecruiterAndStatusCustom(String recruiterId, String status,String title);


    Page<JobPost> findAllByRecruiterIdOrderByCreatedAtDesc(
            String recruiterId,
            Pageable pageable
    );

    List<JobPost> findAllByRecruiterIdOrderByCreatedAtDesc(String recruiterId);

    Optional<JobPost> findByIdAndRecruiterId(String jobPostId, String recruiterId);

    @Query("""
                SELECT CASE WHEN COUNT(j) > 0 THEN true ELSE false END
                FROM JobPost j
                WHERE j.id = :jobPostId
                  AND j.status = 'PUBLIC'
                  AND (j.endedTime IS NULL OR j.endedTime > CURRENT_TIMESTAMP)
            """)
    boolean existsValidJobPost(@Param("jobPostId") String jobPostId);

    @Modifying
    @Transactional
    @Query("UPDATE JobPost j SET j.viewCount = j.viewCount + :views WHERE j.id = :jobPostId")
    void increaseViewCount(@Param("jobPostId") String jobPostId,
                           @Param("views") Long views);


    /* ========================================
       Statistic
       ======================================== */

    // Tổng bài đã đăng
    long countByRecruiterId(String recruiterId);

    // Đang mở tuyển
    @Query("""
        SELECT COUNT(j)
        FROM JobPost j
        WHERE j.recruiter.id = :recruiterId
          AND j.status = 'PUBLIC'
          AND (j.endedTime IS NULL OR j.endedTime > CURRENT_TIMESTAMP)
    """)
    long countOpeningJobs(@Param("recruiterId") String recruiterId);

    // Bị ẩn
    @Query("""
        SELECT COUNT(j)
        FROM JobPost j
        WHERE j.recruiter.id = :recruiterId
          AND j.status <> 'PUBLIC'
    """)
    long countHiddenJobs(@Param("recruiterId") String recruiterId);

    // Hết hạn
    @Query("""
        SELECT COUNT(j)
        FROM JobPost j
        WHERE j.recruiter.id = :recruiterId
          AND j.endedTime IS NOT NULL
          AND j.endedTime <= CURRENT_TIMESTAMP
    """)
    long countExpiredJobs(@Param("recruiterId") String recruiterId);

    @Query("""
    SELECT
        YEAR(j.createdAt)  AS year,
        MONTH(j.createdAt) AS month,
        COUNT(j)           AS count
    FROM JobPost j
    WHERE j.recruiter.id = :recruiterId
      AND j.createdAt >= :fromDate
    GROUP BY
        YEAR(j.createdAt),
        MONTH(j.createdAt)
    ORDER BY
        YEAR(j.createdAt),
        MONTH(j.createdAt)
    """)
    List<RecruiterPostingFrequencyResponse> getPostingFrequencyOfRecruiter(
            @Param("recruiterId") String recruiterId,
            @Param("fromDate") LocalDateTime fromDate
    );



    List<JobPost> findByIsBoostedTrueAndBoostExpiredAtBefore(LocalDateTime boostExpiredAtBefore);
}