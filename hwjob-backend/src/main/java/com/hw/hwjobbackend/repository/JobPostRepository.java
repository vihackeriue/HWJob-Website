package com.hw.hwjobbackend.repository;

import com.hw.hwjobbackend.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, String> {
}
