package com.hw.hwjobbackend.model.dto.response.chat;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipantResponse {
    String userId;
    String username;
    String fullName;
    String imageUrl;
}
