package com.hw.hwjobbackend.dto.response.user;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecruiterProfileResponse {
    String id;
    String name;
    String email;
    String phone;
    String imageUrl;

//    String province;
    String region;

    String description;
    String website;
    String specificAddress;

    boolean isFollowed;
}
