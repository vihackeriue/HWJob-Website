package com.hw.hwjobbackend.repository.token;

import com.hw.hwjobbackend.model.entity.token.InvalidateToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// Lưu trữ token không hợp lệ khi logout

@Repository
public interface InvalidateTokenRepository extends MongoRepository<InvalidateToken, String> {
}
