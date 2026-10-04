package com.aura.godeliver.controller;

import com.aura.godeliver.dto.LoginRequestDto;
import com.aura.godeliver.dto.LoginResponseDto;
import com.aura.godeliver.dto.RegisterRequestDto;
import com.aura.godeliver.dto.RegisterResponseDto;
import com.aura.godeliver.exception.ApiResponse;
import com.aura.godeliver.exception.SuccessCode;
import com.aura.godeliver.service.AuthService;
import com.aura.godeliver.service.MessageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

        private final AuthService authService;
        private final MessageService messageService;

        @PostMapping("/register")
        public ResponseEntity<ApiResponse<RegisterResponseDto>> register(
                        @Valid @RequestBody RegisterRequestDto request) {

                RegisterResponseDto result = authService.register(request);

                ApiResponse<RegisterResponseDto> response = new ApiResponse<>(
                                true,
                                HttpStatus.CREATED.value(),
                                messageService.getMessage(
                                                SuccessCode.USER_REGISTERED.getMessageKey()),
                                List.of(result));

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(response);
        }

        @PostMapping("/login")
        public ResponseEntity<ApiResponse<LoginResponseDto>> login(
                        @Valid @RequestBody LoginRequestDto request) {

                LoginResponseDto result = authService.login(request);

                ApiResponse<LoginResponseDto> response = new ApiResponse<>(
                                true,
                                HttpStatus.OK.value(),
                                messageService.getMessage(
                                                SuccessCode.USER_LOGGED_IN.getMessageKey()),
                                List.of(result));

                return ResponseEntity.ok(response);
        }

}