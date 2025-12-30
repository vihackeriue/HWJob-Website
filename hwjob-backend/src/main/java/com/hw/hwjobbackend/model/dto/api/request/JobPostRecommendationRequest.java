package com.hw.hwjobbackend.model.dto.api.request;

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
    private String summary;
    private String education;
    private List<String> skills;
}
