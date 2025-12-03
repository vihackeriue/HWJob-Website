package com.hw.hwjobbackend.repository.region;

import com.hw.hwjobbackend.model.entity.region.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
}
