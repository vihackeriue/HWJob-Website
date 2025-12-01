package com.hw.hwjobbackend.model.dto.request.job_post;

import com.hw.hwjobbackend.model.enums.JobPostStatusEnum;
import com.hw.hwjobbackend.model.enums.SalaryTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostRequest {

    String title;

    String description;

    Integer quantity;

    Long salary;

    SalaryTypeEnum salaryType;

    JobPostStatusEnum status;

    LocalDateTime endedTime;

    Long levelId;

    Long jobTypeId;

    Long industryId;

    Set<Long> skillIds;

    Integer regionId;
}
