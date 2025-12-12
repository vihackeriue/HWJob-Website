package com.hw.hwjobbackend.controller.chat;


import com.hw.hwjobbackend.model.dto.request.chat.ChatMessageRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.chat.ChatMessageResponse;
import com.hw.hwjobbackend.service.shared.chat.ChatService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class WebSocketChatController {

    ChatService chatService;

    @MessageMapping("/chat.send")
    public ApiResponse<ChatMessageResponse> sendMessage(@Payload ChatMessageRequest request, Principal principal) {

        // Step 1: Kiểm tra authentication
        if (principal == null) {
            // Không xử lý nếu chưa authenticated
            log.error("Principal is null - user not authenticated");
            return ApiResponse.<ChatMessageResponse>builder()
                    .result(null)
                    .build();
        }

        // Step 2: Lấy userId từ Principal (được set trong WebSocketAuthChannelInterceptor)
        String userId = principal.getName();

        log.info("Received message for conversation: {}", request.getConversationId());

        // Step 3: Gọi service với userId
        return ApiResponse.<ChatMessageResponse>builder()
                .result(chatService.createMessage(request, userId))
                .build();
    }
}
