package com.hw.hwjobbackend.dto.request;


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
