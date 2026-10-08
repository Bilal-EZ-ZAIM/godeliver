package com.aura.godeliver.dto.auth;

public record LoginResult(
        LoginResponseDto response,
        String refreshToken
) {
}