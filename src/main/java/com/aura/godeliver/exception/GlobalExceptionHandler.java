package com.aura.godeliver.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // =========================
    // Validation errors - @Valid
    // =========================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(
            MethodArgumentNotValidException ex
    ) {

        List<ApiResponse.ApiError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ApiResponse.ApiError(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        String message = messageSource.getMessage(
                "error.validation",
                null,
                LocaleContextHolder.getLocale()
        );

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                HttpStatus.BAD_REQUEST.value(),
                message,
                List.of(),
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // =========================
    // Constraint validation
    // =========================
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolationException(
            ConstraintViolationException ex
    ) {

        List<ApiResponse.ApiError> errors = ex.getConstraintViolations()
                .stream()
                .map(error -> new ApiResponse.ApiError(
                        error.getPropertyPath().toString(),
                        error.getMessage()
                ))
                .toList();

        String message = messageSource.getMessage(
                "error.validation",
                null,
                LocaleContextHolder.getLocale()
        );

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                HttpStatus.BAD_REQUEST.value(),
                message,
                List.of(),
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // =========================
    // Unexpected errors
    // =========================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(
            Exception ex
    ) {

        String message = messageSource.getMessage(
                "error.internal",
                null,
                LocaleContextHolder.getLocale()
        );

        ApiResponse<Object> response = new ApiResponse<>(
                false,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message,
                List.of(),
                List.of()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}