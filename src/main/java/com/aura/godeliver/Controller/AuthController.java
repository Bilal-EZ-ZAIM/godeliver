package com.aura.godeliver.Controller;

import com.aura.godeliver.dto.RegisterRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @Valid @RequestBody RegisterRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registration endpoint");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("User login endpoint");
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken() {
        return ResponseEntity.ok("Refresh token endpoint");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logout endpoint");
    }
}