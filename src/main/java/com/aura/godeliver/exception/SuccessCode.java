package com.aura.godeliver.exception;

public enum SuccessCode {

    USER_LOGGED_IN("success.user.loggedIn"),
    USER_REGISTERED("success.user.registered");

    private final String messageKey;

    SuccessCode(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}