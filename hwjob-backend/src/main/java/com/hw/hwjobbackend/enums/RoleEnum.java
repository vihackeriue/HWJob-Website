package com.hw.hwjobbackend.enums;

public enum RoleEnum {
    ADMIN,
    RECRUITER,
    CANDIDATE;

    public String withPrefix() {
        return "ROLE_" + this.name();
    }
}
