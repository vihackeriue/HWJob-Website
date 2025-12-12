package com.hw.hwjobbackend.model.dto.response.chat;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageResponse {
    String id;
    String conversationId;
    Boolean isMine;
    String message;
    ParticipantResponse sender;
    Instant createdDate;
}
