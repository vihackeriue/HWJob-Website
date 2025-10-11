package com.hw.hwjobbackend.repository;

import com.hw.hwjobbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findAllByName(String name);

    Optional<Role> findByName(String name);
}
