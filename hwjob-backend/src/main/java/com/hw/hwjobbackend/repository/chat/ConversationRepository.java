package com.hw.hwjobbackend.repository.chat;


import com.hw.hwjobbackend.model.entity.chat.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String> {

    Optional<Conversation> findByParticipantsHash(String participantsHash);

    @Query("{'participants.userId' : ?0}")
    List<Conversation> findAllByParticipantIdsContainsOrderByModifiedDateDesc(String userId);

    @Query(value = "{ '_id': ?0, 'participants.userId': ?1 }",
            fields = "{ '_id': 1 }")
    Optional<Conversation> validateConversationHasCurrentUser(String conversationId, String currentUserId);

    @Query("{ '_id': ?0 }")
    @Update("{ '$set': { 'modifiedDate': ?1 } }")
    void updateModifiedDate(String conversationId, Instant modifiedDate);

}
