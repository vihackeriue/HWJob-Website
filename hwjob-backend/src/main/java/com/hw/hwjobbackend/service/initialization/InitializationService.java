package com.hw.hwjobbackend.service.initialization;

import com.hw.hwjobbackend.model.entity.user.Role;

import java.util.Set;

public interface InitializationService {

    void initializeRoles();

    void createAdminUser();

    void initializeRegion();

    void initializeIndustries();

    void initializeJobTypes();

    void initializeLevels();

    void initializeSkills();
}
