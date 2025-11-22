package com.hw.hwjobbackend.dto.response.user;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateProfileResponse {
    String id;
    String username;
    String name;
    String email;
    String phone;
    String imageUrl;
}
