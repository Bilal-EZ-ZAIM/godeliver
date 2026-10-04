package com.aura.godeliver.dto;

public record LoginResult(
        LoginResponseDto response,
        String refreshToken
) {
}