package com.hw.hwjobbackend.model.dto.response.profile;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecruiterProfileResponse extends UserProfileResponse {
    String website;
    boolean isFollowed;
}
