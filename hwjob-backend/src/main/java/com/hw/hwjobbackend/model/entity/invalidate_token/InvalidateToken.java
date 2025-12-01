package com.hw.hwjobbackend.model.entity.invalidate_token;


import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("invalidate_tokens")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidateToken implements Serializable {
    @Id
    String jwtId;

    @TimeToLive
    Long ttl;
}
