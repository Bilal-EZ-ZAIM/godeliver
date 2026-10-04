
package com.aura.godeliver.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegisterResponseDto(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
