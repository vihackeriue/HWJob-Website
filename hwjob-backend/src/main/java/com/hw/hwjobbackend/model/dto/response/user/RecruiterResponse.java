package com.hw.hwjobbackend.model.dto.response.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecruiterResponse extends UserResponse {
    String description;
    String website;
    String specificAddress;
}
