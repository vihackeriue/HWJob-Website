package com.hw.hwjobbackend.model.entity.job_post;

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
@RedisHash(value = "recommend_jobs", timeToLive = 5)
public class RecommendJobPostCache {
    @Id
    String id;
    List<String> jobPostIds;
}
