package com.hw.hwjobbackend.repository.job_post;

import com.hw.hwjobbackend.model.entity.job_post.JobPostBoostHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostBoostHistoryRepository extends CrudRepository<JobPostBoostHistory, Long> {
}
