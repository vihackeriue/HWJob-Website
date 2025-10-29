package com.hw.hwjobbackend.service;

import com.hw.hwjobbackend.entity.Role;

import java.util.Set;

public interface InitializationService {

    void initializeRolesAndAdmin();

    void initializeRegionData();

    void initializePredefinedRoles();

    void createAdminUser(Set<Role> roles);

    void initializeIndustries();

    void initializeSkills();
}
