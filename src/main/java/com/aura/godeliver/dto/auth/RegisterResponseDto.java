package com.aura.godeliver.dto.auth;

import java.time.Instant;
import java.util.UUID;

public record RegisterResponseDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}