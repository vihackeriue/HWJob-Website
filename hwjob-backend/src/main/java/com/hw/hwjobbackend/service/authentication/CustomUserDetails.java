package com.hw.hwjobbackend.service.authentication;

import com.hw.hwjobbackend.entity.Role;
import com.hw.hwjobbackend.entity.User;
import com.hw.hwjobbackend.enums.RoleEnum;
import com.hw.hwjobbackend.enums.UserStatusEnum;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public record CustomUserDetails(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        if (Objects.isNull(roles) || roles.isEmpty()) {
            return Set.of();
        }
        return roles.stream()
                .map(Role::getName)
                .filter(Objects::nonNull)
                .map(String::toUpperCase)
                .filter(this::isValidRoleEnum)
                .map(name -> new SimpleGrantedAuthority("ROLE_" + name))
                .collect(Collectors.toSet());
    }

    private boolean isValidRoleEnum(String name) {
        try {
            RoleEnum.valueOf(name);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getUserStatus() == UserStatusEnum.ACTIVE;
    }


}
