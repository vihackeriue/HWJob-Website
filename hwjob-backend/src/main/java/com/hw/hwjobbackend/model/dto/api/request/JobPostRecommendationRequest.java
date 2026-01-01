package com.hw.hwjobbackend.model.dto.api.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostRecommendationRequest {
    @JsonProperty("candidate_id")
    String candidateId;
}
