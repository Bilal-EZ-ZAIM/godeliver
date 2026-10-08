package com.aura.godeliver.security;

import com.aura.godeliver.common.response.ApiResponse;
import com.aura.godeliver.exception.AccessTokenExpiredException;
import com.aura.godeliver.exception.ErrorCode;
import com.aura.godeliver.exception.InvalidAccessTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFailureHandler {

    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;

    public void handle(
            HttpServletResponse response,
            RuntimeException exception
    ) throws IOException {

        ErrorCode errorCode;

        if (exception instanceof AccessTokenExpiredException) {

            errorCode = ErrorCode.ACCESS_TOKEN_EXPIRED;

        } else if (exception instanceof InvalidAccessTokenException) {

            errorCode = ErrorCode.INVALID_ACCESS_TOKEN;

        } else {

            errorCode = ErrorCode.AUTHENTICATION_REQUIRED;
        }

        String message = messageSource.getMessage(
                errorCode.getMessageKey(),
                null,
                LocaleContextHolder.getLocale()
        );

        ApiResponse<Object> apiResponse = new ApiResponse<>(
                false,
                HttpStatus.UNAUTHORIZED.value(),
                errorCode.name(),
                message,
                List.of(),
                List.of()
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(
                objectMapper.writeValueAsString(apiResponse)
        );
    }
}