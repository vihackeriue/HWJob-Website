package com.hw.hwjobbackend.service.initialization;

public interface InitializationService {

    void initializeRoles();

    void createAdminUser();

    void initializeRegion();

    void initializeIndustries();

    void initializeJobTypes();

    void initializeLevels();

    void initializeSkills();

    void initializeTestUsers();
}
