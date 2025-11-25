package com.hw.hwjobbackend.model.enums;

public enum RoleEnum {
    ADMIN,
    RECRUITER,
    CANDIDATE;

    public String withPrefix() {
        return "ROLE_" + this.name();
    }
}
