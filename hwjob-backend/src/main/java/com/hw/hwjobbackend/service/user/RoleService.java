package com.hw.hwjobbackend.service.user;

import com.hw.hwjobbackend.entity.Role;

import java.util.Set;

public interface RoleService {

    boolean existsByName(String name);

    Set<Role> getRolesByNames(Set<String> roleNames);
}
