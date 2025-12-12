package com.hw.hwjobbackend.model.dto.request.chat;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChatMessageRequest {

    @NotBlank
    String conversationId;

    @NotBlank
    String message;
}
