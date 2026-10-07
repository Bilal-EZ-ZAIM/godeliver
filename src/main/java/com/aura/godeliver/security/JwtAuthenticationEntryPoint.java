package com.aura.godeliver.security;

import com.aura.godeliver.exception.ApiResponse;
import com.aura.godeliver.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final MessageSource messageSource;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {

        ErrorCode errorCode = ErrorCode.AUTHENTICATION_REQUIRED;

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