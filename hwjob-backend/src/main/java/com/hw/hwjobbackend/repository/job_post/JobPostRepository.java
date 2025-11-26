package com.hw.hwjobbackend.repository.job_post;

import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.enums.JobPostStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, String> {
    @Query("""
                 SELECT jp FROM JobPost jp
                 WHERE jp.status = :status
                   AND (jp.endedTime IS NULL OR jp.endedTime > CURRENT_TIMESTAMP)
                   AND (:industryId IS NULL OR jp.industry.id = :industryId)
                   AND (:levelId IS NULL OR jp.level.id = :levelId)
                   AND (:jobTypeId IS NULL OR jp.jobType.id = :jobTypeId)
                   AND (:regionId IS NULL OR jp.province.code = :regionId)
                 ORDER BY jp.createdAt DESC
            """)
    Page<JobPost> getJobPosts(
            @Param("status") JobPostStatus status,
            @Param("industryId") Long industryId,
            @Param("levelId") Long levelId,
            @Param("jobTypeId") Long jobTypeId,
            @Param("regionId") Integer regionId,
            Pageable pageable
    );
}
