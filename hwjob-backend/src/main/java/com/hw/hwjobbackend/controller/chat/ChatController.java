package com.hw.hwjobbackend.controller.chat;

import com.hw.hwjobbackend.model.dto.request.chat.ChatMessageRequest;
import com.hw.hwjobbackend.model.dto.request.chat.ConversationRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.chat.ChatMessageResponse;
import com.hw.hwjobbackend.model.dto.response.chat.ConversationResponse;
import com.hw.hwjobbackend.service.shared.chat.ChatService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/chats")
public class ChatController {

    ChatService chatService;

    @GetMapping
    public ApiResponse<List<ConversationResponse>> getConversations() {
        return ApiResponse.<List<ConversationResponse>>builder()
                .result(chatService.getAllConversations())
                .build();
    }

    @PostMapping
    public ApiResponse<ConversationResponse> createConversation(
            @Valid @RequestBody ConversationRequest request
    ) {
        return ApiResponse.<ConversationResponse>builder()
                .result(chatService.createConversation(request))
                .build();
    }

    @GetMapping("/{conversationId}/messages")
    public ApiResponse<List<ChatMessageResponse>> getMessages(
            @PathVariable String conversationId
    ) {
        return ApiResponse.<List<ChatMessageResponse>>builder()
                .result(chatService.getMessages(conversationId))
                .build();
    }
}
