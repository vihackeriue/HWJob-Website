package com.hw.hwjobbackend.model.dto.response.review;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AverageRatingResponse {
    Double averageRating;
}
