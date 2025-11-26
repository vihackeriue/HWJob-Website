package com.hw.hwjobbackend.repository.industry;

import com.hw.hwjobbackend.model.entity.industry.Industry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndustryRepository extends JpaRepository<Industry, Long> {
    boolean existsByName(String name);
}
