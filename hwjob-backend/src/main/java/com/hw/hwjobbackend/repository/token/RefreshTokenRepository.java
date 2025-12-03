package com.hw.hwjobbackend.repository.token;

import com.hw.hwjobbackend.model.entity.token.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


// Lưu trữ refreshToken

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    void deleteByUserId(String userId);
}
