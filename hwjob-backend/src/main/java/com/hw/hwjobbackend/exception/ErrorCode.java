package com.hw.hwjobbackend.exception;


import com.hw.hwjobbackend.configuration.internationalization.Translator;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    UNCATEGORIZED_EXCEPTION(9999, "error.uncategorized", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "error.invalid_key", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1002, "error.unauthorized", HttpStatus.FORBIDDEN),


    USERNAME_EXISTED(1003, "error.username_existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1006, "error.username_invalid", HttpStatus.BAD_REQUEST),
    USERNAME_PASSWORD_INVALID(1013, "error.username_password_invalid", HttpStatus.BAD_REQUEST),

    PASSWORD_INVALID(1007, "error.password_invalid", HttpStatus.BAD_REQUEST),

    USER_NOT_EXISTED(1004, "error.username_not_existed", HttpStatus.NOT_FOUND),
    USER_EXISTED(1012, "error.user_existed", HttpStatus.BAD_REQUEST),

    UNAUTHENTICATED(1005, "error.unauthenticated", HttpStatus.UNAUTHORIZED),

    PHONE_INVALID(1008, "error.phone_invalid", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1009, "error.email_invalid", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(1010, "error.role_not_existed", HttpStatus.NOT_FOUND),
    EMAIL_EXISTED(1011, "error.email_existed", HttpStatus.BAD_REQUEST),


    CREATE_USER_FAIL(1014, "error.create_user_fail", HttpStatus.BAD_REQUEST),
    PROVINCE_NOT_EXISTED(1015, "error.province_not_existed", HttpStatus.BAD_REQUEST),

    COUNTRY_NOT_EXISTED(1017, "error.country_not_existed", HttpStatus.BAD_REQUEST),
    WARD_NOT_EXISTED(1018, "error.ward_not_existed", HttpStatus.BAD_REQUEST),

    LEVEL_NOT_BLANK(1021, "error.level_not_blank", HttpStatus.BAD_REQUEST),
    LEVEL_EXISTED(1022, "error.level_existed", HttpStatus.BAD_REQUEST),
    LEVEL_NOT_EXISTED(1023, "error.level_not_existed", HttpStatus.BAD_REQUEST),

    JOB_TYPE_NOT_BLANK(1024, "error.job_type_not_blank", HttpStatus.BAD_REQUEST),
    JOB_TYPE_EXISTED(1025, "error.job_type_existed", HttpStatus.BAD_REQUEST),
    JOB_TYPE_NOT_EXISTED(1026, "error.job_type_not_existed", HttpStatus.BAD_REQUEST),

    JOB_TYPE_CODE_NOT_BLANK(1027, "error.job_type_code_not_blank", HttpStatus.BAD_REQUEST),
    JOB_TYPE_CODE_EXISTED(1028, "error.job_type_code_existed", HttpStatus.BAD_REQUEST),
    JOB_TYPE_CODE_NOT_EXISTED(1029, "error.job_type_code_not_existed", HttpStatus.BAD_REQUEST),

    INDUSTRY_NOT_BLANK(1030, "error.industry_not_blank", HttpStatus.BAD_REQUEST),
    INDUSTRY_EXISTED(1031, "error.industry_existed", HttpStatus.BAD_REQUEST),
    INDUSTRY_NOT_EXISTED(1032, "error.industry_not_existed", HttpStatus.BAD_REQUEST),

    FILE_NOT_FOUND(1008, "File not found", HttpStatus.NOT_FOUND),


    JOB_POST_NOT_EXISTED(1033, "error.job_post_not_existed", HttpStatus.NOT_FOUND),
    JOB_POST_ALREADY_APPLIED(1034, "error.job_post_already_applied", HttpStatus.BAD_REQUEST),
    ;


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
