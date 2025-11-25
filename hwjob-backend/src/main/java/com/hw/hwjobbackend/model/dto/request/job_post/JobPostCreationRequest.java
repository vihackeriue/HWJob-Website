package com.hw.hwjobbackend.model.dto.request.job_post;

import com.hw.hwjobbackend.model.enums.SalaryType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostCreationRequest {

    String title;

    String description;

    Integer quantity;

    Long salary;

    SalaryType salaryType;

    LocalDateTime endedTime;

    Long levelId;

    Long jobTypeId;

    Long industryId;

    Integer regionId;
}
