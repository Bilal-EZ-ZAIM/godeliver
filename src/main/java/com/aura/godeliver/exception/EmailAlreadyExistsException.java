package com.aura.godeliver.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    private final ErrorCode errorCode = ErrorCode.EMAIL_ALREADY_EXISTS;

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}