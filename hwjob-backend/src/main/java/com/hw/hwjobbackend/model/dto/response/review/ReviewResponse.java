package com.hw.hwjobbackend.model.dto.response.review;

import com.hw.hwjobbackend.model.dto.response.user.UserReviewResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewResponse {
    Long id;
    UserReviewResponse reviewer;
    double rating;
    LocalDateTime createdAt;
}
