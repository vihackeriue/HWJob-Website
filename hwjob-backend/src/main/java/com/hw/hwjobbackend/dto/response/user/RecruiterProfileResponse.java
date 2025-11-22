package com.hw.hwjobbackend.dto.response.user;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecruiterProfileResponse {
    String id;
    String name;

    String email;
    String phone;
    String imageUrl;

    String region;

    String description;
    String website;
    String specificAddress;

    boolean isFollowed;
}
