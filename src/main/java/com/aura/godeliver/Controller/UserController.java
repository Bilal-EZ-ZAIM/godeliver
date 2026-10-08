package com.aura.godeliver.controller;

import com.aura.godeliver.common.response.ApiResponse;
import com.aura.godeliver.dto.auth.RegisterResponseDto;
import com.aura.godeliver.service.user.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<RegisterResponseDto>> getCurrentUser(
            Authentication authentication) {

        UUID userId = UUID.fromString(
                authentication.getName());

        RegisterResponseDto user = userService.getCurrentUser(userId);

        ApiResponse<RegisterResponseDto> response = new ApiResponse<>(
                true,
                HttpStatus.OK.value(),
                "User retrieved successfully",
                List.of(user));

        return ResponseEntity.ok(response);
    }
}