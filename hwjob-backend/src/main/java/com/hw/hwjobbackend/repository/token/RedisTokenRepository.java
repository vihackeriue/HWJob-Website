package com.hw.hwjobbackend.repository.token;

import com.hw.hwjobbackend.model.entity.InvalidateToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisTokenRepository extends CrudRepository<InvalidateToken, String> {
}
