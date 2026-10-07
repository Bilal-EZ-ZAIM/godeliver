package com.aura.godeliver.exception;

public class InvalidAccessTokenException extends RuntimeException {

    private final ErrorCode errorCode;

    public InvalidAccessTokenException() {
        this.errorCode = ErrorCode.INVALID_ACCESS_TOKEN;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}