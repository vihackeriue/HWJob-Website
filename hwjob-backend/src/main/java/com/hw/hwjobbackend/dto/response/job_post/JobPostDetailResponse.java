package com.hw.hwjobbackend.dto.response.job_post;


import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String salary;
    String address;

    String recruiterId;
    String recruiterName;
    String recruiterImageUrl;

    String status;
    boolean isApplied;
    boolean isSaved;

}
