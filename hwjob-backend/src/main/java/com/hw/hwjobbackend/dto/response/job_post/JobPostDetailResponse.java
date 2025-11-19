package com.hw.hwjobbackend.dto.response.job_post;


import com.hw.hwjobbackend.enums.JobPostStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostDetailResponse {
    String id;
    String title;
    String description;

    String industry;
    String level;
    String jobType;

    String quantity;
    Long salary;

    String recruiterId;
    String recruiterName;
    String recruiterImageUrl;

    JobPostStatus status;
    Date createdAt;
    LocalDateTime endedTime;

    String region;
    
    boolean isApplied;
    boolean isSaved;

}
