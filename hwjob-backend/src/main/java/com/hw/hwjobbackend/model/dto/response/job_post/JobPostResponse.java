package com.hw.hwjobbackend.model.dto.response.job_post;


import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostResponse {
    String id;
    String title;

    Integer quantity;
    String industry;
    String level;
    String jobType;

    String region;

    Boolean isBoosted;

    ApplicationStatusEnum applicationStatus;
    JobPostRecruiterProfileResponse recruiter;

}
