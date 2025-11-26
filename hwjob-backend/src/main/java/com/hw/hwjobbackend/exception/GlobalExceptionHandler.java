package com.hw.hwjobbackend.exception;

import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";

    /**
     * Bắt các lỗi hệ thống chưa được xử lý riêng
     */
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handlingUnhandledException(Exception exception) {
        log.error("Unhandled exception: ", exception);

        ErrorCode errorCode = ErrorCode.UNCATEGORIZED_EXCEPTION;

        return buildErrorResponse(errorCode);
    }

    /**
     * Bắt lỗi AppException (Đã được định nghĩa trong hệ thống)
     */
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return buildErrorResponse(errorCode);
    }

    /**
     * Lỗi đăng nhập sai (username hoặc password)
     */
    @ExceptionHandler(value = {
            BadCredentialsException.class,
            InternalAuthenticationServiceException.class,
            UsernameNotFoundException.class
    })
    public ResponseEntity<ApiResponse<?>> handlingBadCredentialsException(Exception exception) {
        log.error("Login failed: {}", exception.getMessage());

        ErrorCode errorCode = ErrorCode.USERNAME_PASSWORD_INVALID;
        return buildErrorResponse(errorCode);
    }

    /**
     * Lỗi Authentication: chưa đăng nhập, JWT sai hoặc hết hạn
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handlingAuthenticationException(AuthenticationException exception) {
        log.error("Authentication exception: {}", exception.getMessage());

        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
        return buildErrorResponse(errorCode);
    }

    /**
     * Lỗi AUTHORIZATION (Không có quyền truy cập)
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<?>> handlingAccessDeniedException(AccessDeniedException exception) {
        log.error("Access denied: {}", exception.getMessage());
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return buildErrorResponse(errorCode);
    }


    /**
     * Bắt lỗi validate @Valid
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handlingValidation(MethodArgumentNotValidException exception) {

        // Lấy key message từ annotation (ví dụ: USERNAME_INVALID)
        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        Map<String, Object> attributes = null;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
            var constraintViolation = exception.getBindingResult()
                    .getAllErrors()
                    .getFirst()
                    .unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
            log.info("Validation attributes: {}", attributes);
        } catch (IllegalArgumentException ignored) {
        }

        // Thay thế biến trong message nếu có {min}, {max}
        String message = errorCode.getLocalizedMessage();
        if (Objects.nonNull(attributes)) {
            message = mapAttribute(message, attributes);
        }

        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(message)
                .build();

        return ResponseEntity.badRequest().body(apiResponse);
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponse(ErrorCode errorCode) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getLocalizedMessage())
                .build();

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}

