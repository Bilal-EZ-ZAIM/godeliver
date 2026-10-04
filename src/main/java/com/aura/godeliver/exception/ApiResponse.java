package com.aura.godeliver.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ApiResponse<T> {

    private boolean success;
    private int status;
    private String code;
    private String message;
    private List<T> data;
    private List<ApiError> errors;

    // Success response
    public ApiResponse(
            boolean success,
            int status,
            String message,
            List<T> data
    ) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
        this.errors = Collections.emptyList();
    }

    // Error response
    public ApiResponse(
            boolean success,
            int status,
            String code,
            String message,
            List<T> data,
            List<ApiError> errors
    ) {
        this.success = success;
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
        this.errors = errors;
    }

    @Getter
    @Setter
    public static class ApiError {

        private String field;
        private String message;

        public ApiError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}