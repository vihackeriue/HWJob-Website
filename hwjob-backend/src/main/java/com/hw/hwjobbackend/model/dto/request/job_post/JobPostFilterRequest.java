package com.hw.hwjobbackend.model.dto.request.job_post;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostFilterRequest {
    Long industryId;
    Long levelId;
    Long jobTypeId;
    Integer regionId;

}
