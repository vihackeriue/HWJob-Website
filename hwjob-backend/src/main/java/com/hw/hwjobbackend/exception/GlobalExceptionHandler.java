package com.hw.hwjobbackend.exception;

import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{([^}]+)}");

    // ========================================
    // SYSTEM EXCEPTION HANDLERS
    // ========================================

    /**
     * Bắt các lỗi hệ thống chưa được xử lý riêng
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiResponse<?> handleUnhandledException(Exception exception) {
        log.error("Unhandled exception: ", exception);
        return buildErrorResponse(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }

    /**
     * Bắt lỗi AppException (Đã được định nghĩa trong hệ thống)
     */
    @ExceptionHandler(AppException.class)
    ApiResponse<?> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        log.warn("Application exception: code={}, message={}", errorCode.getCode(), errorCode.getMessage());
        return buildErrorResponse(errorCode);
    }

    // ========================================
    // AUTHENTICATION & AUTHORIZATION HANDLERS
    // ========================================

    /**
     * Lỗi đăng nhập sai (username hoặc password)
     */
    @ExceptionHandler({
            BadCredentialsException.class,
            InternalAuthenticationServiceException.class,
            UsernameNotFoundException.class,
            DisabledException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiResponse<?> handleBadCredentialsException(Exception exception) {
        log.warn("Login failed: {}", exception.getClass().getSimpleName());
        return buildErrorResponse(ErrorCode.USERNAME_PASSWORD_INVALID);
    }

    /**
     * Lỗi Authentication: chưa đăng nhập, JWT sai hoặc hết hạn
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ApiResponse<?> handleAuthenticationException(AuthenticationException exception) {
        log.warn("Authentication failed: {}", exception.getClass().getSimpleName());
        return buildErrorResponse(ErrorCode.UNAUTHENTICATED);
    }

    /**
     * Lỗi AUTHORIZATION (Không có quyền truy cập)
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ApiResponse<?> handleAccessDeniedException(AccessDeniedException exception) {
        log.warn("Access denied: {}", exception.getClass().getSimpleName());
        return buildErrorResponse(ErrorCode.UNAUTHORIZED);
    }

    // ========================================
    // VALIDATION EXCEPTION HANDLERS
    // ========================================

    /**
     * Bắt lỗi validate @Valid
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiResponse<?> handleValidationException(MethodArgumentNotValidException exception) {

        var fieldError = exception.getFieldError();
        if (fieldError == null) {
            log.error("Validation exception with null field error");
            return buildErrorResponse(ErrorCode.INVALID_KEY);
        }

        String enumKey = fieldError.getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;

        try {
            errorCode = ErrorCode.valueOf(enumKey);

            var allErrors = exception.getBindingResult().getAllErrors();
            if (!allErrors.isEmpty()) {
                var constraintViolation = allErrors.getFirst()
                        .unwrap(ConstraintViolation.class);
                attributes = extractAttributes(constraintViolation);

                if (log.isDebugEnabled() && attributes != null && !attributes.isEmpty()) {
                    log.debug("Validation attributes: {}", attributes);
                }
            }
        } catch (IllegalArgumentException e) {
            log.warn("Invalid error code key: {}", enumKey);
        } catch (Exception e) {
            log.error("Error processing validation exception", e);
        }

        String message = errorCode.getLocalizedMessage();
        if (attributes != null && !attributes.isEmpty()) {
            message = mapAttributes(message, attributes);
        }

        return ApiResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build();
    }

    // ========================================
    // HTTP REQUEST EXCEPTION HANDLERS
    // ========================================

    /**
     * Lỗi HTTP request không hợp lệ (missing param, wrong type, etc.)
     */
    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiResponse<?> handleBadRequestException(Exception exception) {
        log.warn("Bad request - type: {}, message: {}",
                exception.getClass().getSimpleName(),
                exception.getMessage());
        return buildErrorResponse(ErrorCode.INVALID_KEY);
    }

    /**
     * Lỗi HTTP method không được hỗ trợ
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    ApiResponse<?> handleMethodNotAllowed(HttpRequestMethodNotSupportedException exception) {
        log.warn("Method not allowed: {} {}",
                exception.getMethod(),
                exception.getSupportedHttpMethods());
        return buildErrorResponse(ErrorCode.INVALID_KEY);
    }

    // ========================================
    // PRIVATE HELPER METHODS
    // ========================================

    /**
     * Build error response từ ErrorCode
     */
    private ApiResponse<?> buildErrorResponse(ErrorCode errorCode) {
        return ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getLocalizedMessage())
                .build();
    }

    /**
     * Extract attributes từ ConstraintViolation
     */
    private Map<String, Object> extractAttributes(ConstraintViolation<?> violation) {
        return violation.getConstraintDescriptor().getAttributes();
    }

    /**
     * Map các attributes vào message placeholders ({min}, {max}, {value},...)
     */
    private String mapAttributes(String message, Map<String, Object> attributes) {
        if (message == null || attributes == null || attributes.isEmpty()) {
            return message;
        }

        StringBuilder result = new StringBuilder();
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(message);

        while (matcher.find()) {
            String key = matcher.group(1);
            Object value = attributes.get(key);

            if (value != null) {
                String replacement = Matcher.quoteReplacement(String.valueOf(value));
                matcher.appendReplacement(result, replacement);
            }
        }
        matcher.appendTail(result);

        return result.toString();
    }
}