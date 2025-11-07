package com.hw.hwjobbackend.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobTypeEnum {

    FULL_TIME("Toàn thời gian"),
    PART_TIME("Bán thời gian"),
    INTERNSHIP("Thực tập"),
    REMOTE("Làm việc từ xa"),
    CONTRACT("Hợp đồng ngắn hạn"),
    FREELANCE("Cộng tác viên"),
    TEMPORARY("Thời vụ"),
    APPRENTICESHIP("Học việc");

    private final String name;
}
