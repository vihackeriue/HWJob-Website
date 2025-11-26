package com.hw.hwjobbackend.repository.job_type;

import com.hw.hwjobbackend.model.entity.job_type.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTypeRepository extends JpaRepository<JobType, Long> {
    boolean existsByName(String name);

    boolean existsByCode(String code);
}
