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
public class CandidateIndexingRequest {
    @JsonProperty("candidate_id")
    String candidateId;

    String summary;
    String education;
    List<String> skills;
}
