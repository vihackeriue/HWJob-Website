package com.hw.hwjobbackend.service.initialization;

import com.hw.hwjobbackend.model.entity.user.Role;

import java.util.Set;

public interface InitializationService {

    void initializeRolesAndAdmin();

    void initializeRegionData();

    void initializeRoles();

    void createAdminUser(Set<Role> roles);

    void initializeIndustries();

    void initializeJobTypes();

    void initializeLevel();
}
