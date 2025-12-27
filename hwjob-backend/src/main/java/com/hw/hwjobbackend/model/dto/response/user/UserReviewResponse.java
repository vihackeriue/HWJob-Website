package com.hw.hwjobbackend.model.dto.response.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserReviewResponse {
    String id;
    String fullName;
    String imageUrl;
}
