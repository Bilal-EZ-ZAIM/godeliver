package com.aura.godeliver.dto;

import com.aura.godeliver.entity.RefreshToken;

public record RefreshTokenResult(
        String rawToken,
        RefreshToken refreshToken
) {
}