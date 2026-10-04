package com.aura.godeliver.service;

import com.aura.godeliver.dto.RegisterResponseDto;

import java.util.UUID;

public interface UserService {

    RegisterResponseDto getCurrentUser(UUID userId);
}