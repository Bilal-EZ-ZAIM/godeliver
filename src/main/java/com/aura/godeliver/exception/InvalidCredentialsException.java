package com.aura.godeliver.exception;

import lombok.Getter;

@Getter
public class InvalidCredentialsException extends RuntimeException {

    private final ErrorCode errorCode = ErrorCode.INVALID_CREDENTIALS;
}