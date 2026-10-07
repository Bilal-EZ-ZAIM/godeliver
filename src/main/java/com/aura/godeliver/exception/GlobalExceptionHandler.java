package com.aura.godeliver.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import java.util.List;

@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

        private final MessageSource messageSource;

        // =========================================================================
        // Validation errors - @Valid
        // =========================================================================

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse<Object>> handleValidationException(
                        MethodArgumentNotValidException ex) {

                log.warn(
                                "Validation failed: object={}, errors={}",
                                ex.getObjectName(),
                                ex.getBindingResult().getErrorCount());

                List<ApiResponse.ApiError> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> new ApiResponse.ApiError(
                                                error.getField(),
                                                error.getDefaultMessage()))
                                .toList();

                ApiResponse<Object> response = new ApiResponse<>(
                                false,
                                HttpStatus.BAD_REQUEST.value(),
                                ErrorCode.VALIDATION_FAILED.name(),
                                messageSource.getMessage(
                                                ErrorCode.VALIDATION_FAILED.getMessageKey(),
                                                null,
                                                LocaleContextHolder.getLocale()),
                                List.of(),
                                errors);

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(response);
        }

        // =========================================================================
        // Constraint validation
        // =========================================================================

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ApiResponse<Object>> handleConstraintViolationException(
                        ConstraintViolationException ex) {

                log.warn(
                                "Constraint validation failed: violations={}",
                                ex.getConstraintViolations().size());

                List<ApiResponse.ApiError> errors = ex.getConstraintViolations()
                                .stream()
                                .map(error -> new ApiResponse.ApiError(
                                                error.getPropertyPath().toString(),
                                                error.getMessage()))
                                .toList();

                ApiResponse<Object> response = new ApiResponse<>(
                                false,
                                HttpStatus.BAD_REQUEST.value(),
                                ErrorCode.VALIDATION_FAILED.name(),
                                messageSource.getMessage(
                                                ErrorCode.VALIDATION_FAILED.getMessageKey(),
                                                null,
                                                LocaleContextHolder.getLocale()),
                                List.of(),
                                errors);

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(response);
        }

        // =========================================================================
        // Resource not found - 404
        // =========================================================================

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(
                        ResourceNotFoundException ex) {

                log.warn(
                                "Resource not found: {}",
                                ex.getMessage());

                ApiResponse<Object> response = new ApiResponse<>(
                                false,
                                HttpStatus.NOT_FOUND.value(),
                                ErrorCode.RESOURCE_NOT_FOUND.name(),
                                messageSource.getMessage(
                                                ErrorCode.RESOURCE_NOT_FOUND.getMessageKey(),
                                                null,
                                                LocaleContextHolder.getLocale()),
                                List.of(),
                                List.of());

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(response);
        }

        @ExceptionHandler(NoResourceFoundException.class)
        public ResponseEntity<ApiResponse<Object>> handleNoResourceFoundException(
                        NoResourceFoundException ex) {

                log.warn(
                                "Endpoint not found: {}",
                                ex.getResourcePath());

                ApiResponse<Object> response = new ApiResponse<>(
                                false,
                                HttpStatus.NOT_FOUND.value(),
                                ErrorCode.ENDPOINT_NOT_FOUND.name(),
                                messageSource.getMessage(
                                                ErrorCode.ENDPOINT_NOT_FOUND.getMessageKey(),
                                                null,
                                                LocaleContextHolder.getLocale()),
                                List.of(),
                                List.of());

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(response);
        }

        @ExceptionHandler(NoHandlerFoundException.class)
        public ResponseEntity<ApiResponse<Object>> handleNoHandlerFoundException(
                        NoHandlerFoundException ex) {

                log.warn(
                                "Endpoint not found: method={}, url={}",
                                ex.getHttpMethod(),
                                ex.getRequestURL());

                ApiResponse<Object> response = new ApiResponse<>(
                                false,
                                HttpStatus.NOT_FOUND.value(),
                                ErrorCode.ENDPOINT_NOT_FOUND.name(),
                                messageSource.getMessage(
                                                ErrorCode.ENDPOINT_NOT_FOUND.getMessageKey(),
                                                null,
                                                LocaleContextHolder.getLocale()),
                                List.of(),
                                List.of());

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(response);
        }
        // =========================================================================
        // Email already exists - 409
        // =========================================================================

        @ExceptionHandler(EmailAlreadyExistsException.class)
        public ResponseEntity<ApiResponse<Object>> handleEmailAlreadyExistsException(
                        EmailAlreadyExistsException ex) {

                String message = messageSource.getMessage(
                                ex.getErrorCode().getMessageKey(),
                                null,
                                LocaleContextHolder.getLocale());

                ApiResponse<Object> response = new ApiResponse<>(
                                false,
                                HttpStatus.CONFLICT.value(),
                                ex.getErrorCode().name(),
                                message,
                                List.of(),
                                List.of());

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(response);
        }

        // =========================================================================
        // Invalid access token - 401
        // =========================================================================

        @ExceptionHandler(InvalidAccessTokenException.class)
        public ResponseEntity<ApiResponse<Object>> handleInvalidAccessTokenException(
                        InvalidAccessTokenException ex) {

                log.warn("Invalid or expired access token");

                String message = messageSource.getMessage(
                                ex.getErrorCode().getMessageKey(),
                                null,
                                LocaleContextHolder.getLocale());

                ApiResponse<Object> response = new ApiResponse<>(
                                false,
                                HttpStatus.UNAUTHORIZED.value(),
                                ex.getErrorCode().name(),
                                message,
                                List.of(),
                                List.of());

                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(response);
        }

        @ExceptionHandler(InvalidRefreshTokenException.class)
        public ResponseEntity<ApiResponse<Object>> handleInvalidRefreshTokenException(
                        InvalidRefreshTokenException exception) {

                ErrorCode errorCode = ErrorCode.INVALID_REFRESH_TOKEN;

                String message = messageSource.getMessage(
                                errorCode.getMessageKey(),
                                null,
                                LocaleContextHolder.getLocale());

                ApiResponse<Object> response = new ApiResponse<>(
                                false,
                                HttpStatus.UNAUTHORIZED.value(),
                                errorCode.name(),
                                message,
                                List.of(),
                                List.of());

                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(response);
        }

        // =========================================================================
        // Unexpected errors - 500
        // =========================================================================

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<Object>> handleGenericException(
                        Exception ex) {

                log.error(
                                "Unexpected internal server error",
                                ex);

                ApiResponse<Object> response = new ApiResponse<>(
                                false,
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                ErrorCode.INTERNAL_ERROR.name(),
                                messageSource.getMessage(
                                                ErrorCode.INTERNAL_ERROR.getMessageKey(),
                                                null,
                                                LocaleContextHolder.getLocale()),
                                List.of(),
                                List.of());
                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(response);
        }

}