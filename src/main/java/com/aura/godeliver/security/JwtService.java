package com.aura.godeliver.security;

import com.aura.godeliver.entity.User;

public interface JwtService {

    String generateAccessToken(User user);

    long getAccessTokenExpirationSeconds();

    String extractUserId(String token);

    boolean isTokenValid(String token);
}