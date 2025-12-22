package com.hw.hwjobbackend.model.enums;

public enum WorkStatusEnum {
    PENDING,
    IN_PROGRESS,    // đang làm
    SUBMITTED,
    COMPLETED,     // hoàn thành
    REJECTED,
    PAID,          // đã thanh toán
    CANCELLED,     // hủy
    DISPUTED       // tranh chấp
}
