package com.hw.hwjobbackend.repository;

import com.hw.hwjobbackend.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    boolean existsByName(String name);
}
