package com.hw.hwjobbackend.repository.region;

import com.hw.hwjobbackend.model.entity.region.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
}
