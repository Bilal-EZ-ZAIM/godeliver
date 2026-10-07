package com.aura.godeliver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

        @Id
        @Generated
        @ColumnDefault("gen_random_uuid()")
        @Column(name = "id", nullable = false, updatable = false)
        private UUID id;

        @Column(name = "first_name", nullable = false, length = 100)
        private String firstName;

        @Column(name = "last_name", nullable = false, length = 100)
        private String lastName;

        @Column(name = "email", nullable = false, length = 255, unique = true)
        private String email;

        @Column(name = "phone", nullable = false, length = 30, unique = true)
        private String phone;

        @Column(name = "password_hash", nullable = false, length = 255)
        private String passwordHash;

        @Column(name = "description", length = 500)
        private String description;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private Instant createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at", nullable = false)
        private Instant updatedAt;
}