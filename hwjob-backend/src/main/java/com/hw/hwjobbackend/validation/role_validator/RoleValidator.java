package com.hw.hwjobbackend.validation.role_validator;


import com.hw.hwjobbackend.model.enums.RoleEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;

public class RoleValidator implements ConstraintValidator<RoleConstraint, Set<String>> {


    @Override
    public void initialize(RoleConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Set<String> roles, ConstraintValidatorContext context) {
        if (roles == null || roles.isEmpty()) return false;
        return roles.stream().allMatch(role ->
                Arrays.stream(RoleEnum.values())
                        .anyMatch(e -> e.name().equalsIgnoreCase(role))
        );
    }
}
