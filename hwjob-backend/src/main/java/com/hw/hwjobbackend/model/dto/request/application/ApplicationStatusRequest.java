package com.hw.hwjobbackend.model.dto.request.application;


import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import com.hw.hwjobbackend.model.enums.SalaryTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatusRequest {
    ApplicationStatusEnum status;

    // CHỈ dùng khi status = ASSIGNED
    Long agreedSalary;
    SalaryTypeEnum salaryType;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
