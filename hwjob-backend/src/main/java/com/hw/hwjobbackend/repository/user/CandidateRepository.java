package com.hw.hwjobbackend.repository.user;

import com.hw.hwjobbackend.model.entity.user.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, String> {

    Optional<Candidate> findByUsername(String username);
}
