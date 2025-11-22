package com.hw.hwjobbackend.dto.response.job_post;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.hw.hwjobbackend.dto.response.user.RecruiterProfileResponse;
import com.hw.hwjobbackend.enums.JobPostStatus;
import com.hw.hwjobbackend.enums.SalaryType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobPostDetailResponse {
    String id;
    String title;
    String description;
    String quantity;
    Long salary;
    SalaryType salaryType;

    String industry;
    String level;
    String jobType;

    RecruiterProfileResponse recruiter;

    JobPostStatus status;
    Date createdAt;
    LocalDateTime endedTime;

    String region;

    Boolean isApplied;
    Boolean isSaved;

}
