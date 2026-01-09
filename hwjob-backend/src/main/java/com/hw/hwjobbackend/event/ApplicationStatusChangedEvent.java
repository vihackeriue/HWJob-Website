package com.hw.hwjobbackend.event;

import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;

public record ApplicationStatusChangedEvent(
        String email,
        String jobTitle,
        ApplicationStatusEnum status
) {
}
