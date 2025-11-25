package com.hw.hwjobbackend.repository.application;


import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.application.ApplicationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, ApplicationId> {
    boolean existsApplicationById(ApplicationId id);
}
