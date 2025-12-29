package com.hw.hwjobbackend.repository.user;

import com.hw.hwjobbackend.model.entity.user.Role;
import com.hw.hwjobbackend.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
    Set<Role> findAllByName(RoleEnum name);
}
