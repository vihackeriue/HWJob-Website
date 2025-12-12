package com.hw.hwjobbackend.model.entity.chat;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collection = "chat_messages")
public class ChatMessage {
    @MongoId
    String id;

    @Indexed
    String conversationId;

    String message;

//    String linkFile;

    Participant sender;

    @Indexed
    Instant createdDate;
}
