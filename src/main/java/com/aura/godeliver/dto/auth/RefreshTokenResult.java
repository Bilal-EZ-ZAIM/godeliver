package com.aura.godeliver.dto.auth;

import com.aura.godeliver.entity.RefreshToken;

public record RefreshTokenResult(
        String rawToken,
        RefreshToken refreshToken
) {
}