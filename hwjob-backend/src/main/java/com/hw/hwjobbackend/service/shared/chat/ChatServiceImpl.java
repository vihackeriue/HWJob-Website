package com.hw.hwjobbackend.service.shared.chat;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.chat.ChatMessageRequest;
import com.hw.hwjobbackend.model.dto.request.chat.ConversationRequest;
import com.hw.hwjobbackend.model.dto.response.chat.ChatMessageResponse;
import com.hw.hwjobbackend.model.dto.response.chat.ConversationResponse;
import com.hw.hwjobbackend.model.dto.response.chat.ParticipantResponse;
import com.hw.hwjobbackend.model.entity.chat.ChatMessage;
import com.hw.hwjobbackend.model.entity.chat.Conversation;
import com.hw.hwjobbackend.model.entity.chat.Participant;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.repository.chat.ConversationRepository;
import com.hw.hwjobbackend.repository.chat.MessageRepository;
import com.hw.hwjobbackend.repository.user.UserRepository;
import com.hw.hwjobbackend.service.mapper.chat.ChatMapper;
import com.hw.hwjobbackend.util.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ChatServiceImpl implements ChatService {

    ConversationRepository conversationRepository;
    MessageRepository messageRepository;
    UserRepository userRepository;

    ChatMapper chatMapper;

    SimpMessagingTemplate messagingTemplate;

    @Override
    public List<ConversationResponse> getAllConversations() {
        // Step 1: Lấy userId từ SecurityContext (HTTP request)
        String userId = SecurityUtils.getCurrentUserId();

        // Step 2: Query MongoDB - tìm tất cả conversation có user tham gia
        List<Conversation> conversations = conversationRepository
                .findAllByParticipantIdsContainsOrderByModifiedDateDesc(userId);

        // Step 3: Transform sang Response DTO
        return conversations.stream()
                .map(conversation -> toConversationResponse(conversation, userId))
                .toList();
    }

    @Override
    public ConversationResponse createConversation(ConversationRequest request) {

        // Step 1: Lấy current user từ MySQL
        User currentUser = userRepository.findById(SecurityUtils.getCurrentUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Step 2: Lấy participant user từ MySQL
        User participant = userRepository.findById(request.getParticipantIds().iterator().next())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Step 3: Tạo participantHash để tránh duplicate conversation
        //         Ví dụ: {"user1", "user2"} → "user1_user2" (sorted)
        Set<String> userIds = Set.of(currentUser.getId(), participant.getId());
        String participantHash = generateParticipantHash(userIds);

        // Step 4: Kiểm tra conversation đã tồn tại chưa
        Conversation conversation = conversationRepository.findByParticipantsHash(participantHash)
                .orElseGet(() -> {
                    // Nếu chưa tồn tại → tạo mới
                    Set<Participant> participants = Set.of(
                            chatMapper.toParticipant(currentUser),
                            chatMapper.toParticipant(participant));

                    Conversation newConversation = Conversation.builder()
                            .type(request.getType())
                            .participantsHash(participantHash)
                            .participants(participants)
                            .createdDate(Instant.now())
                            .modifiedDate(Instant.now())
                            .build();
                    return conversationRepository.save(newConversation);
                });

        return toConversationResponse(conversation, currentUser.getId());
    }

    @Override
    public List<ChatMessageResponse> getMessages(String conversationId) {

        String userId = SecurityUtils.getCurrentUserId();

        // Step 1: Validate user có quyền xem conversation này không
        conversationRepository
                .validateConversationHasCurrentUser(conversationId, userId)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        // Step 2: Lấy tất cả messages, sắp xếp theo thời gian giảm dần
        List<ChatMessage> messages = messageRepository
                .findAllByConversationIdOrderByCreatedDateDesc(conversationId);

        // Step 3: Transform sang Response DTO
        return messages.stream()
                .map(message -> toChatMessageResponse(message, userId))
                .toList();
    }

    @Override
    public ChatMessageResponse createMessage(ChatMessageRequest request, String userId) {

        // Step 1: Validate user có trong conversation không
        conversationRepository
                .validateConversationHasCurrentUser(request.getConversationId(), userId)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        // Step 2: Tạo ChatMessage entity
        ChatMessage chatMessage = chatMapper.toChatMessage(request);

        // Step 3: Lấy User từ MySQL để set sender info
        User user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        // Step 4: Set sender và timestamp
        chatMessage.setSender(chatMapper.toParticipant(user));
        chatMessage.setCreatedDate(Instant.now());

        // Step 5: Save to MongoDB
        chatMessage = messageRepository.save(chatMessage);
        // Cập nhật modifiedDate của conversation
        conversationRepository.updateModifiedDate(
                request.getConversationId(),
                Instant.now()
        );

        // Step 6: Transform sang Response DTO
        ChatMessageResponse response = chatMapper.toChatMessageResponse(chatMessage);
        response.setSender(chatMapper.toParticipantResponse(chatMessage.getSender()));

        // Step 7: Broadcast (phát) message đến tất cả subscribers
        broadcastMessageToConversation(request.getConversationId(), response, userId);
        response.setIsMine(true);

        return response;
    }


    private String generateParticipantHash(Set<String> userIds) {
        return String.join("_", new HashSet<>(userIds).stream().sorted().toList());
    }

    private ConversationResponse toConversationResponse(Conversation conversation, String currentUserId) {
        // 1. Map entity → DTO
        ConversationResponse conversationResponse = chatMapper.toConversationResponse(conversation);

        // 2. Map participants
        List<ParticipantResponse> participantResponses = conversation.getParticipants().stream()
                .map(chatMapper::toParticipantResponse)
                .toList();

        conversationResponse.setParticipants(participantResponses);

        log.debug("conversationResponse: {}", conversationResponse);

        // 3. Set conversation image & name = thông tin của người còn lại (không phải current user)
        //    Ví dụ: Nếu conversation giữa A và B, khi A xem thì hiện avatar + tên của B
        conversationResponse.getParticipants().stream()
                .filter(participant -> !participant.getUserId().equals(currentUserId))
                .findFirst().ifPresent(participant -> {
                    conversationResponse.setConversationImage(participant.getImageUrl());
                    conversationResponse.setConversationName(participant.getFullName());
                });

        log.debug("conversationResponse: {}", conversationResponse);

        return conversationResponse;
    }

    private ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage, String currentUserId) {

        ChatMessageResponse chatMessageResponse = chatMapper.toChatMessageResponse(chatMessage);

        // Set isMine = true nếu sender là current user
        chatMessageResponse.setIsMine(currentUserId.equals(chatMessage.getSender().getUserId()));

        return chatMessageResponse;
    }

    public void broadcastMessageToConversation(String conversationId, ChatMessageResponse message, String senderId) {
        // Lấy conversation để biết danh sách participants
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        // Gửi message riêng cho từng participant
        for (Participant participant : conversation.getParticipants()) {
            String participantUserId = participant.getUserId();

            // Clone response và set isMine đúng cho từng user
            ChatMessageResponse personalizedMessage = ChatMessageResponse.builder()
                    .id(message.getId())
                    .conversationId(message.getConversationId())
                    .message(message.getMessage())
                    .sender(message.getSender())
                    .createdDate(message.getCreatedDate())
                    .isMine(participantUserId.equals(senderId))
                    .build();

            // Gửi đến user-specific destination: /user/{userId}/queue/messages
            messagingTemplate.convertAndSendToUser(
                    participantUserId,
                    "/queue/messages",
                    personalizedMessage
            );
        }
    }

}
