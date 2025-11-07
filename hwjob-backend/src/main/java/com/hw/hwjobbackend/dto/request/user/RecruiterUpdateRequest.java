package com.hw.hwjobbackend.dto.request.user;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecruiterUpdateRequest extends UserUpdateRequest {
    String description;
    String website;
    String specificAddress;
}
