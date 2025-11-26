package com.hw.hwjobbackend.model.dto.response.job_post;


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

    String imageUrl;
    String recruiterName;
}
