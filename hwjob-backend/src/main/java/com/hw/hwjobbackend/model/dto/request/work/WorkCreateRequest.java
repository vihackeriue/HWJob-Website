package com.hw.hwjobbackend.model.dto.request.work;

import com.hw.hwjobbackend.model.enums.SalaryTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkCreateRequest {
    String candidateId;
    String jobPostId;
    Long agreedSalary;
    SalaryTypeEnum salaryType;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
