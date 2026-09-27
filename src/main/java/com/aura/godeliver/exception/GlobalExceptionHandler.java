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
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

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
                                "Validation failed",
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
                                "Validation failed",
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
                                ex.getMessage(),
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
                                "Endpoint not found",
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
                                "Endpoint not found",
                                List.of(),
                                List.of());

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
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
                                "An unexpected error occurred",
                                List.of(),
                                List.of());

                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(response);
        }
}