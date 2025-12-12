package com.hw.hwjobbackend.model.dto.request.chat;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TypingNotificationRequest {
    String conversationId;
    String userId;
    String username;
    boolean isTyping;
}
