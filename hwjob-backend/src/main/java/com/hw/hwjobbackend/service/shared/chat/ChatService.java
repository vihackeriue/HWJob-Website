package com.hw.hwjobbackend.service.shared.chat;

import com.hw.hwjobbackend.model.dto.request.chat.ChatMessageRequest;
import com.hw.hwjobbackend.model.dto.request.chat.ConversationRequest;
import com.hw.hwjobbackend.model.dto.response.chat.ChatMessageResponse;
import com.hw.hwjobbackend.model.dto.response.chat.ConversationResponse;

import java.util.List;

public interface ChatService {

    List<ConversationResponse> getAllConversations();

    ConversationResponse createConversation(ConversationRequest request);

    List<ChatMessageResponse> getMessages(String conversationId);

    ChatMessageResponse createMessage(ChatMessageRequest request, String userId);
}
