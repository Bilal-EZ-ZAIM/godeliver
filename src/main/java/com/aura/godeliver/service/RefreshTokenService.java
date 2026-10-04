package com.aura.godeliver.service;

import com.aura.godeliver.dto.RefreshTokenResult;
import com.aura.godeliver.entity.RefreshToken;

import java.util.UUID;

public interface RefreshTokenService {

    RefreshTokenResult create(UUID userId);

    RefreshToken verify(String rawToken);

    void revoke(RefreshToken refreshToken);
}