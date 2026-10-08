package com.aura.godeliver.common.message;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

    public String getMessage(String messageKey) {
        return messageSource.getMessage(
                messageKey,
                null,
                LocaleContextHolder.getLocale());
    }

    public String getMessage(String messageKey, Object... args) {
        return messageSource.getMessage(
                messageKey,
                args,
                LocaleContextHolder.getLocale());
    }

    public String getMessage(String messageKey, @Nullable Object[] args, Locale locale) {
        return messageSource.getMessage(messageKey, args, locale);
    }
}