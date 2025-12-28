package com.hw.hwjobbackend.model.dto.response.user;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecruiterHomeResponse {
    String id;
    String username;
    String fullName;
    String imageUrl;
    Long jobPostingNumber;
    Integer followerNumber;
}
