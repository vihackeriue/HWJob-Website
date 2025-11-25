package com.hw.hwjobbackend.repository.region;

import com.hw.hwjobbackend.model.entity.region.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> { }