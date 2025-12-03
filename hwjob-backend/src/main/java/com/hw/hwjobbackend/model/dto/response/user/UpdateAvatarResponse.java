package com.hw.hwjobbackend.model.dto.response.user;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateAvatarResponse {
    String imageUrl;
}
