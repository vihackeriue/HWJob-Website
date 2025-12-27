package com.hw.hwjobbackend.model.enums;

public enum WorkStatusEnum {
    PENDING,
    IN_PROGRESS,    // đang làm
    SUBMITTED,
    PAID,     // hoàn thành
    REJECTED,
    CANCELLED,     // hủy
    DISPUTED       // tranh chấp
}
