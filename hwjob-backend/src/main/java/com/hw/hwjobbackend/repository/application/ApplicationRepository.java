package com.hw.hwjobbackend.repository.application;


import com.hw.hwjobbackend.entity.JobPost;
import com.hw.hwjobbackend.entity.application.Application;
import com.hw.hwjobbackend.entity.application.ApplicationId;
import com.hw.hwjobbackend.entity.user.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, ApplicationId> {
    boolean existsApplicationById(ApplicationId id);
}
