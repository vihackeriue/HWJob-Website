package com.hw.hwjobbackend.repository.invalidate_token;

import com.hw.hwjobbackend.model.entity.invalidate_token.InvalidateToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisTokenRepository extends CrudRepository<InvalidateToken, String> {
}
