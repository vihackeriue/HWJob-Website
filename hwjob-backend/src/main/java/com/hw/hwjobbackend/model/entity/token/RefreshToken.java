package com.hw.hwjobbackend.model.entity.token;


import org.springframework.data.annotation.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("refresh_tokens")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshToken implements Serializable {

    @Id
    String jwtId;

    @Indexed
    String userId;

    @TimeToLive
    Long ttl;
}
