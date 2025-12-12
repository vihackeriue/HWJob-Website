package com.hw.hwjobbackend.repository.chat;

import com.hw.hwjobbackend.model.entity.chat.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findAllByConversationIdOrderByCreatedDateDesc(String conversationId);

}
