package com.hw.hwjobbackend.validator.role_validator;


import com.hw.hwjobbackend.constant.PredefinedRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
                role.equalsIgnoreCase(PredefinedRole.CANDIDATE_ROLE) ||
                        role.equalsIgnoreCase(PredefinedRole.RECRUITER_ROLE)
        );
    }
}
