package com.aura.godeliver.exception;

public enum ErrorCode {

    EMAIL_ALREADY_EXISTS("error.email.alreadyExists"),
    RESOURCE_NOT_FOUND("error.resource.notFound"),
    VALIDATION_ERROR("error.validation"),
    ENDPOINT_NOT_FOUND("error.endpoint.notFound"),
    INTERNAL_ERROR("error.internal"),
    VALIDATION_FAILED("error.validation.failed"),
    INVALID_ACCESS_TOKEN("error.auth.invalidAccessToken"),
    INVALID_REFRESH_TOKEN("error.auth.invalidRefreshToken"),
    ACCESS_TOKEN_EXPIRED("error.auth.accessTokenExpired"),
    AUTHENTICATION_REQUIRED("error.authentication.required"),
    INVALID_CREDENTIALS("error.auth.invalidCredentials");

    private final String messageKey;

    ErrorCode(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}