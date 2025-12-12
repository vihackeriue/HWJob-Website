package com.hw.hwjobbackend.service.mapper.chat;

import com.hw.hwjobbackend.model.dto.request.chat.ChatMessageRequest;
import com.hw.hwjobbackend.model.dto.response.chat.ChatMessageResponse;
import com.hw.hwjobbackend.model.dto.response.chat.ConversationResponse;
import com.hw.hwjobbackend.model.dto.response.chat.ParticipantResponse;
import com.hw.hwjobbackend.model.entity.chat.ChatMessage;
import com.hw.hwjobbackend.model.entity.chat.Conversation;
import com.hw.hwjobbackend.model.entity.chat.Participant;
import com.hw.hwjobbackend.model.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Mapping(source = "id", target = "userId")
    Participant toParticipant(User user);

    ParticipantResponse toParticipantResponse(Participant participant);

    ConversationResponse toConversationResponse(Conversation conversation);

    ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage);

    ChatMessage toChatMessage(ChatMessageRequest chatMessageRequest);

}
