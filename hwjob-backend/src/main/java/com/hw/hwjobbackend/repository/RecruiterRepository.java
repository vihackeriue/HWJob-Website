package com.hw.hwjobbackend.repository;

import com.hw.hwjobbackend.entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, String> {
    Optional<Recruiter> findByUsername(String username);
}
