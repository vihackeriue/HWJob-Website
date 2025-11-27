package com.hw.hwjobbackend.model.dto.response.job_post;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostRecruiterProfileResponse {
    String id;
    String fullName;
    String imageUrl;
}
