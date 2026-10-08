package com.aura.godeliver.service.user;

import java.util.UUID;

import com.aura.godeliver.dto.auth.RegisterResponseDto;

public interface UserService {

    RegisterResponseDto getCurrentUser(UUID userId);
}