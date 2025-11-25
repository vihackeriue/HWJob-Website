package com.hw.hwjobbackend.repository.job_post;

import com.hw.hwjobbackend.model.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, String> {
}
