package com.hw.hwjobbackend.model.dto.request.chat;


import com.hw.hwjobbackend.model.enums.ConversationTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConversationRequest {
    ConversationTypeEnum type;

    @Size(min = 1)
    @NotNull
    Set<String> participantIds;

}
