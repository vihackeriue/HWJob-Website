package com.hw.hwjobbackend.exception;


import lombok.Getter;

@Getter
public class AppException extends RuntimeException {

    private final ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getLocalizedMessage());
        this.errorCode = errorCode;
    }
}

