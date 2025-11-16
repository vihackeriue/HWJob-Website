package com.hw.hwjobbackend.dto.request.job_post;

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

    Long salaryMin;

    String salaryType;

    LocalDateTime endedTime;

    Long levelId;

    Long jobTypeId;

    Long industryId;

    Integer provinceId;

}
