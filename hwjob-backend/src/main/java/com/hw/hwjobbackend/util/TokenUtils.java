package com.hw.hwjobbackend.util;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility class để xử lý JWT token
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenUtils {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final int BEARER_PREFIX_LENGTH = 7;

    public static String extractToken(String authorizationHeader) {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        if (!authorizationHeader.startsWith(BEARER_PREFIX)) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = authorizationHeader.substring(BEARER_PREFIX_LENGTH).trim();
        if (token.isBlank()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return token;
    }
}