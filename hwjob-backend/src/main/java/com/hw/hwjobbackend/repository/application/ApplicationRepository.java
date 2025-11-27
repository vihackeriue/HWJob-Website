package com.hw.hwjobbackend.repository.application;


import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, ApplicationId> {
    boolean existsApplicationById(ApplicationId id);

    Page<Application> findByJobPost(JobPost jobPost, Pageable pageable);

    List<Application> findAllByJobPost(JobPost jobPost);

    Application findApplicationByCandidate(Candidate candidate);
}
