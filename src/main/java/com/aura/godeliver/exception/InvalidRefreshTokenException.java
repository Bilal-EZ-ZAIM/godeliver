package com.aura.godeliver.exception;

import lombok.Getter;

@Getter
public class InvalidRefreshTokenException extends RuntimeException {

    private final ErrorCode errorCode = ErrorCode.INVALID_REFRESH_TOKEN;

    public InvalidRefreshTokenException() {
        super();
    }
}