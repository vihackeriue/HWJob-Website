package com.hw.hwjobbackend.exception;


import com.hw.hwjobbackend.configuration.internationalization.Translator;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    // ========================================
    // SYSTEM ERRORS (9xxx)
    // ========================================
    UNCATEGORIZED_EXCEPTION(9999, "error.uncategorized", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(9001, "error.invalid_key", HttpStatus.BAD_REQUEST),

    // ========================================
    // AUTHENTICATION & AUTHORIZATION (10xx)
    // ========================================
    UNAUTHENTICATED(1001, "error.unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1002, "error.unauthorized", HttpStatus.FORBIDDEN),
    USERNAME_PASSWORD_INVALID(1003, "error.username_password_invalid", HttpStatus.BAD_REQUEST),

    USER_STATUS_REQUIRED(1037, "error.user_status_required", HttpStatus.BAD_REQUEST),
    CANNOT_CHANGE_OWN_STATUS(1038, "error.cannot_change_own_status", HttpStatus.BAD_REQUEST),

    // ========================================
    // USER (11xx)
    // ========================================

    USERNAME_EXISTED(1101, "error.username_existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1102, "error.username_invalid", HttpStatus.BAD_REQUEST),


    PASSWORD_INVALID(1103, "error.password_invalid", HttpStatus.BAD_REQUEST),
    OLD_PASSWORD_INVALID(1104, "error.old_password_invalid", HttpStatus.BAD_REQUEST),


    USER_NOT_EXISTED(1105, "error.user_not_existed", HttpStatus.NOT_FOUND),

    USER_EXISTED(1106, "error.user_existed", HttpStatus.BAD_REQUEST),
    CREATE_USER_FAIL(1107, "error.create_user_fail", HttpStatus.BAD_REQUEST),


    PHONE_INVALID(1108, "error.phone_invalid", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1109, "error.email_invalid", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1110, "error.email_existed", HttpStatus.BAD_REQUEST),


    ROLE_NOT_EXISTED(1111, "error.role_not_existed", HttpStatus.NOT_FOUND),

    // ========================================
    // REGION (12xx)
    // ========================================
    REGION_NOT_EXISTED(1201, "error.region_not_existed", HttpStatus.NOT_FOUND),
    // ========================================
    // JOB LEVEL (13xx)
    // ========================================
    LEVEL_NOT_BLANK(1301, "error.level_not_blank", HttpStatus.BAD_REQUEST),
    LEVEL_EXISTED(1302, "error.level_existed", HttpStatus.BAD_REQUEST),
    LEVEL_NOT_EXISTED(1303, "error.level_not_existed", HttpStatus.NOT_FOUND),

    // ========================================
    // JOB TYPE (14xx)
    // ========================================
    JOB_TYPE_NOT_BLANK(1401, "error.job_type_not_blank", HttpStatus.BAD_REQUEST),
    JOB_TYPE_EXISTED(1402, "error.job_type_existed", HttpStatus.BAD_REQUEST),
    JOB_TYPE_NOT_EXISTED(1403, "error.job_type_not_existed", HttpStatus.NOT_FOUND),

    JOB_TYPE_CODE_NOT_BLANK(1404, "error.job_type_code_not_blank", HttpStatus.BAD_REQUEST),
    JOB_TYPE_CODE_EXISTED(1405, "error.job_type_code_existed", HttpStatus.BAD_REQUEST),
    JOB_TYPE_CODE_NOT_EXISTED(1406, "error.job_type_code_not_existed", HttpStatus.NOT_FOUND),

    // ========================================
    // INDUSTRY (15xx)
    // ========================================
    INDUSTRY_NOT_BLANK(1501, "error.industry_not_blank", HttpStatus.BAD_REQUEST),
    INDUSTRY_EXISTED(1502, "error.industry_existed", HttpStatus.BAD_REQUEST),
    INDUSTRY_NOT_EXISTED(1503, "error.industry_not_existed", HttpStatus.NOT_FOUND),

    // ========================================
    // JOB POST (16xx)
    // ========================================
    JOB_POST_NOT_EXISTED(1601, "error.job_post_not_existed", HttpStatus.NOT_FOUND),
    JOB_POST_ALREADY_APPLIED(1602, "error.job_post_already_applied", HttpStatus.BAD_REQUEST),
    JOB_POST_ALREADY_SAVED(1603, "error.job_post_already_saved", HttpStatus.BAD_REQUEST),
    JOB_POST_EXPIRED(1604, "error.job_post_expired", HttpStatus.BAD_REQUEST),

    // ========================================
    // FILE (17xx)
    // ========================================
    FILE_NOT_FOUND(1701, "error.file_not_found", HttpStatus.NOT_FOUND),

    // ========================================
    // SKILL (18xx)
    // ========================================
    SKILL_EXISTED(1801, "error.skill_existed", HttpStatus.BAD_REQUEST),
    SKILL_NOT_EXISTED(1802, "error.skill_not_existed", HttpStatus.NOT_FOUND),


    //    APPLICATION
    INVALID_APPLICATION_STATUS(1901,"error.invalid_application_status", HttpStatus.BAD_REQUEST),
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
