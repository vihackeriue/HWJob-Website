package com.hw.hwjobbackend.model.dto.api.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostRecommendationRequest {
    @JsonProperty("candidate_id")
    String candidateId;

    @JsonProperty("saved_job_ids")
    List<String> savedJobPostIds;

    @JsonProperty("n_results")
    Integer nResults;
}
