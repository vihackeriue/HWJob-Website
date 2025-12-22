package com.hw.hwjobbackend.model.dto.response.work;

import com.hw.hwjobbackend.model.enums.SalaryTypeEnum;
import com.hw.hwjobbackend.model.enums.WorkStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkOverviewResponse {
    String workId;

    String jobTitle;
    String recruiterName;

    Long agreedSalary;
    SalaryTypeEnum salaryType;

    LocalDateTime startTime;
    LocalDateTime endTime;

    WorkStatusEnum status;
}
