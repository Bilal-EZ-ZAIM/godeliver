package com.aura.godeliver.dto;

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