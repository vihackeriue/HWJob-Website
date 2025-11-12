package com.hw.hwjobbackend.service.initialization;

import com.hw.hwjobbackend.entity.Role;

import java.util.Set;

public interface InitializationService {

    void initializeRolesAndAdmin();

    void initializeRegionData();

    void initializeRoles();

    void createAdminUser(Set<Role> roles);

    void initializeIndustries();

    void initializeSkills();

    void initializeJobTypes();
}
