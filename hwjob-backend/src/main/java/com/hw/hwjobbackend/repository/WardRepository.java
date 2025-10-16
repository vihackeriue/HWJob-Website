package com.hw.hwjobbackend.repository;

import com.hw.hwjobbackend.entity.Province;
import com.hw.hwjobbackend.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> {
    List<Ward> findByProvince(Province province);
}