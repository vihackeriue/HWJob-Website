package com.hw.hwjobbackend.model.dto.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostIndexingRequest {
    @JsonProperty("job_id")
    String jobId;

    String title;
    String description;
    List<String> skills;
    String level;

    @JsonProperty("ended_time")
    LocalDateTime endedTime;

    String status;

}
