package com.hw.hwjobbackend.model.dto.response.work;

import com.hw.hwjobbackend.model.dto.response.job_post.JobPostRecruiterProfileResponse;
import com.hw.hwjobbackend.model.enums.WorkStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkResponse {
    String workId;

    String jobPostId;
    String title;

    Integer quantity;
    String industry;
    String level;
    String jobType;
    String region;

    WorkStatusEnum workStatus;

    JobPostRecruiterProfileResponse recruiter;

}
