package com.aura.godeliver.service.auth;

import com.aura.godeliver.dto.auth.LoginRequestDto;
import com.aura.godeliver.dto.auth.LoginResponseDto;
import com.aura.godeliver.dto.auth.LoginResult;
import com.aura.godeliver.dto.auth.RegisterRequestDto;
import com.aura.godeliver.dto.auth.RegisterResponseDto;

public interface AuthService {

    RegisterResponseDto register(RegisterRequestDto request);

    LoginResult login(LoginRequestDto request);

    LoginResponseDto refresh(String refreshToken);

    void logout(String refreshToken);

}