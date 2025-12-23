package com.hw.hwjobbackend.model.dto.response.job_post;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationResponse;
import com.hw.hwjobbackend.model.dto.response.profile.RecruiterProfileResponse;
import com.hw.hwjobbackend.model.dto.response.skill.SkillResponse;
import com.hw.hwjobbackend.model.dto.response.work.WorkOverviewResponse;
import com.hw.hwjobbackend.model.enums.JobPostStatusEnum;
import com.hw.hwjobbackend.model.enums.SalaryTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

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
    SalaryTypeEnum salaryType;

    String industry;
    String level;
    String jobType;

    JobPostRecruiterProfileResponse recruiter;

    JobPostStatusEnum status;
    Date createdAt;
    LocalDateTime endedTime;

    String region;

    Set<SkillResponse> skills;

    ApplicationResponse application;

    WorkOverviewResponse work;

    Boolean isSaved;

}
