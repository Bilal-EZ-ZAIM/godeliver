package com.aura.godeliver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "refresh_tokens",
        indexes = {
                @Index(
                        name = "idx_refresh_tokens_user_id",
                        columnList = "user_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken {

    @Id
    @Generated
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(
            name = "token_hash",
            nullable = false,
            unique = true,
            length = 255
    )
    private String tokenHash;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    @CreationTimestamp
    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private Instant createdAt;
}