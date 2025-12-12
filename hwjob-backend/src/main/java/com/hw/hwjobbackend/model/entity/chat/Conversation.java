package com.hw.hwjobbackend.model.entity.chat;

import com.hw.hwjobbackend.model.enums.ConversationTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "conversations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Conversation {

    @MongoId
    String id;

    ConversationTypeEnum type;

    // Đảm bảo chỉ có một conversation giữa 2 người với nhau, không trùng lặp
    @Indexed(unique = true)
    String participantsHash;

    Set<Participant> participants;

    Instant createdDate;

    Instant modifiedDate;
}
