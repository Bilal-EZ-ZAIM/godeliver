package com.aura.godeliver.service.impl;

import com.aura.godeliver.dto.RefreshTokenResult;
import com.aura.godeliver.entity.RefreshToken;
import com.aura.godeliver.repository.RefreshTokenRepository;
import com.aura.godeliver.security.TokenHashService;
import com.aura.godeliver.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenHashService tokenHashService;

    @Value("${security.jwt.refresh-token-expiration}")
    private long refreshTokenExpirationSeconds;

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    public RefreshTokenResult create(UUID userId) {

        String rawToken = generateToken();

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUserId(userId);

        refreshToken.setTokenHash(
                tokenHashService.hash(rawToken)
        );

        refreshToken.setExpiresAt(
                Instant.now().plusSeconds(
                        refreshTokenExpirationSeconds
                )
        );

        RefreshToken savedToken =
                refreshTokenRepository.save(refreshToken);

        return new RefreshTokenResult(
                rawToken,
                savedToken
        );
    }

    @Override
    public RefreshToken verify(String rawToken) {

        String tokenHash = tokenHashService.hash(rawToken);

        RefreshToken refreshToken =
                refreshTokenRepository.findByTokenHash(tokenHash)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Invalid refresh token"
                                )
                        );

        if (refreshToken.getRevokedAt() != null) {
            throw new IllegalArgumentException(
                    "Refresh token has been revoked"
            );
        }

        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException(
                    "Refresh token has expired"
            );
        }

        return refreshToken;
    }

    @Override
    public void revoke(RefreshToken refreshToken) {

        refreshToken.setRevokedAt(Instant.now());

        refreshTokenRepository.save(refreshToken);
    }

    private String generateToken() {

        byte[] randomBytes = new byte[64];

        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }
}