package com.hw.hwjobbackend.repository;

import com.hw.hwjobbackend.entity.Country;
import com.hw.hwjobbackend.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
    List<Province> findByCountry(Country country);
}
