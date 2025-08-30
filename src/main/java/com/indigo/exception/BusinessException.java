package com.indigo.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends Exception {
    private final HttpStatus status;
    private final String errorCode;

    public BusinessException(String message, String errorCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
