package com.aura.godeliver.dto.auth;

public record LoginResponseDto(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}