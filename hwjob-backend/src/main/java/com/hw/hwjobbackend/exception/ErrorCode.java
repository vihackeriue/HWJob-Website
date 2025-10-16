package com.hw.hwjobbackend.exception;


import com.hw.hwjobbackend.configuration.Translator;
import lombok.Getter;
import org.mapstruct.Mapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "error.uncategorized", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "error.invalid_key", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1002, "error.unauthorized", HttpStatus.FORBIDDEN),
    USERNAME_EXISTED(1003, "error.username_existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1004, "error.username_not_existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1005, "error.unauthenticated", HttpStatus.UNAUTHORIZED),
    USERNAME_INVALID(1006, "error.username_invalid", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1007, "error.password_invalid", HttpStatus.BAD_REQUEST),
    PHONE_INVALID(1008, "error.phone_invalid", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1009, "error.email_invalid", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(1010, "error.role_not_existed", HttpStatus.NOT_FOUND),
    EMAIL_EXISTED(1011, "error.email_existed", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1012, "error.user_existed", HttpStatus.BAD_REQUEST),
    USERNAME_PASSWORD_INVALID(1013, "error.username_password_invalid", HttpStatus.BAD_REQUEST),
    CREATE_USER_FAIL(1014,"error.create_user_fail" , HttpStatus.BAD_REQUEST),
    PROVINCE_NOT_EXISTED(1015,"error.province_not_existed" , HttpStatus.BAD_REQUEST),;


    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    // translator message
    public String getLocalizedMessage() {
        return Translator.toLocale(this.message);
    }
}
