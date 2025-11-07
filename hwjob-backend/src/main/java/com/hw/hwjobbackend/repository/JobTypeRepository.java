package com.hw.hwjobbackend.repository;

import com.hw.hwjobbackend.entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobTypeRepository extends JpaRepository<JobType, Long> {
    boolean existsByName(String name);

    boolean existsByCode(String code);
}
