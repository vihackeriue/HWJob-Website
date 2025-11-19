package com.hw.hwjobbackend.dto.response.job_post;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class SaveJobPostResponse {
    String id;
}
