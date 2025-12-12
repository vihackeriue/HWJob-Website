package com.hw.hwjobbackend.model.dto.response.chat;


import com.hw.hwjobbackend.model.enums.ConversationTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationResponse {
    String id;
    ConversationTypeEnum type;
    String participantsHash;
    String conversationImage;
    String conversationName;
    List<ParticipantResponse> participants;
    Instant createdDate;
    Instant modifiedDate;
}
