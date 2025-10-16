package com.hw.hwjobbackend.service.implement;

import com.hw.hwjobbackend.entity.Role;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.repository.RoleRepository;
import com.hw.hwjobbackend.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImpl implements RoleService {

    RoleRepository roleRepository;


    @Override
    public boolean existsByName(String name) {
        return roleRepository.findByName(name).isPresent();
    }

    @Override
    public Set<Role> getRolesByNames(Set<String> roleNames) {
        return roleNames.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> {
                            log.error("Role not found: {}", roleName);
                            return new AppException(ErrorCode.ROLE_NOT_EXISTED);
                        }))
                .collect(Collectors.toSet());
    }
}
