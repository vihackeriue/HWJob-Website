package com.hw.hwjobbackend.repository;

import com.hw.hwjobbackend.entity.InvalidateToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisTokenRepository extends CrudRepository<InvalidateToken, String> {
}
