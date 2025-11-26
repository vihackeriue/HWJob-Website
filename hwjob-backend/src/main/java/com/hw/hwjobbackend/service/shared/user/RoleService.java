package com.hw.hwjobbackend.service.shared.user;

import com.hw.hwjobbackend.model.entity.user.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getRolesByNames(Set<String> roleNames);
}
