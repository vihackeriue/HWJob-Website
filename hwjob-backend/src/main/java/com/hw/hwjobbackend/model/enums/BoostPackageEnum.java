package com.hw.hwjobbackend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum BoostPackageEnum {
    THREE_DAYS("3_days", 3, 5),
    SEVEN_DAYS("7_days", 7, 10),
    THIRTY_DAYS("30_days", 30, 20);

    private final String key;
    private final int days;
    private final int discountPercent;

    public static Optional<BoostPackageEnum> fromKey(String key) {
        return Arrays.stream(values())
                .filter(p -> p.key.equalsIgnoreCase(key))
                .findFirst();
    }
}
