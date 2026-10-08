package com.aura.godeliver.controller;

import com.aura.godeliver.common.message.MessageService;
import com.aura.godeliver.common.response.ApiResponse;
import com.aura.godeliver.dto.auth.LoginRequestDto;
import com.aura.godeliver.dto.auth.LoginResponseDto;
import com.aura.godeliver.dto.auth.LoginResult;
import com.aura.godeliver.dto.auth.RegisterRequestDto;
import com.aura.godeliver.dto.auth.RegisterResponseDto;
import com.aura.godeliver.exception.SuccessCode;
import com.aura.godeliver.service.auth.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
                        @Valid @RequestBody LoginRequestDto request,
                        HttpServletResponse httpResponse) {

                LoginResult result = authService.login(request);

                Cookie refreshTokenCookie = new Cookie(
                                "refresh_token",
                                result.refreshToken());

                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setSecure(false); // Set to true in production
                refreshTokenCookie.setPath("/api/v1/auth");

                httpResponse.addCookie(refreshTokenCookie);

                ApiResponse<LoginResponseDto> response = new ApiResponse<>(
                                true,
                                HttpStatus.OK.value(),
                                messageService.getMessage(
                                                SuccessCode.USER_LOGGED_IN.getMessageKey()),
                                List.of(result.response()));

                return ResponseEntity.ok(response);
        }

        @PostMapping("/refresh")
        public ResponseEntity<ApiResponse<LoginResponseDto>> refresh(
                        @CookieValue(name = "refresh_token") String refreshToken) {

                LoginResponseDto result = authService.refresh(refreshToken);

                ApiResponse<LoginResponseDto> response = new ApiResponse<>(
                                true,
                                HttpStatus.OK.value(),
                                "Token refreshed successfully",
                                List.of(result));

                return ResponseEntity.ok(response);
        }

        @PostMapping("/logout")
        public ResponseEntity<ApiResponse<Void>> logout(
                        @CookieValue(name = "refresh_token") String refreshToken,
                        HttpServletResponse httpResponse) {

                authService.logout(refreshToken);

                Cookie refreshTokenCookie = new Cookie(
                                "refresh_token",
                                null);

                refreshTokenCookie.setHttpOnly(true);
                refreshTokenCookie.setSecure(false);
                refreshTokenCookie.setPath("/api/v1/auth");
                refreshTokenCookie.setMaxAge(0);

                httpResponse.addCookie(refreshTokenCookie);

                ApiResponse<Void> response = new ApiResponse<>(
                                true,
                                HttpStatus.OK.value(),
                                "Logout successful",
                                List.of());

                return ResponseEntity.ok(response);
        }
}