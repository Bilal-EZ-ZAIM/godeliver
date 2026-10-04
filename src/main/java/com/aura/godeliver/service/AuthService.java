package com.aura.godeliver.service;

import com.aura.godeliver.dto.LoginRequestDto;
import com.aura.godeliver.dto.LoginResponseDto;
import com.aura.godeliver.dto.LoginResult;
import com.aura.godeliver.dto.RegisterRequestDto;
import com.aura.godeliver.dto.RegisterResponseDto;

public interface AuthService {

    RegisterResponseDto register(RegisterRequestDto request);

    LoginResult login(LoginRequestDto request);
}