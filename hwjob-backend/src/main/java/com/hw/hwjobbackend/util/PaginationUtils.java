package com.hw.hwjobbackend.util;

import com.hw.hwjobbackend.constant.PaginationConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Utility class để xử lý pagination
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaginationUtils {

    public static Pageable buildPageable(int page, int size) {
        int validPage = Math.max(page, PaginationConstants.DEFAULT_PAGE);
        int validSize = normalizeSize(size);
        return PageRequest.of(validPage, validSize);
    }

    public static int normalizeSize(int size) {
        if (size <= 0) {
            return PaginationConstants.DEFAULT_SIZE;
        }
        return Math.min(size, PaginationConstants.MAX_SIZE);
    }

    public static int toZeroBasedPage(int page) {
        return Math.max(page - 1, 0);
    }

    public static int toOneBasedPage(int page) {
        return page + 1;
    }
}