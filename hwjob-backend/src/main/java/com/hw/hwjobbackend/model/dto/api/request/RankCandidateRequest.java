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
public class RankCandidateRequest {
    String title;
    String description;
    List<String> skills;
    String level;

    @JsonProperty("pending_candidate_ids")
    List<Integer> pendingCandidateIds;
}
