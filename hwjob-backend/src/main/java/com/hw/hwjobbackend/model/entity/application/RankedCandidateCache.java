package com.hw.hwjobbackend.model.entity.application;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RedisHash(value = "ranked_candidates", timeToLive = 900) // 15 minutes
public class RankedCandidateCache {
    @Id
    String jobPostId;
    List<String> candidateIds;
}
