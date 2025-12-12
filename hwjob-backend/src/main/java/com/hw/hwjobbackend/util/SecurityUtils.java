package com.hw.hwjobbackend.util;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.enums.RoleEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Utility class xử lý các thao tác liên quan đến Security Context
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtils {

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getPrincipal());
    }

    public static String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return authentication.getName();
    }

    public static RoleEnum getCurrentUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return authentication.getAuthorities()
                .stream()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .map(RoleEnum::valueOf)
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHORIZED));
    }

}