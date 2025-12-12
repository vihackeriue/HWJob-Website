package com.hw.hwjobbackend.model.entity.chat;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Participant {
    String userId;
    String username;
    String fullName;
    String imageUrl;
}
