package com.aura.godeliver.dto;

public record LoginResponseDto(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}