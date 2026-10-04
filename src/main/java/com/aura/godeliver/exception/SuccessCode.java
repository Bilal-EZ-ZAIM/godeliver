package com.aura.godeliver.exception;

public enum SuccessCode {

    USER_REGISTERED("success.user.registered");

    private final String messageKey;

    SuccessCode(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}