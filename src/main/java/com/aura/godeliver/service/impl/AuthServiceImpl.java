package com.aura.godeliver.service.impl;

import com.aura.godeliver.dto.LoginRequestDto;
import com.aura.godeliver.dto.LoginResponseDto;
import com.aura.godeliver.dto.LoginResult;
import com.aura.godeliver.dto.RefreshTokenResult;
import com.aura.godeliver.dto.RegisterRequestDto;
import com.aura.godeliver.dto.RegisterResponseDto;
import com.aura.godeliver.entity.RefreshToken;
import com.aura.godeliver.entity.User;
import com.aura.godeliver.exception.EmailAlreadyExistsException;
import com.aura.godeliver.exception.InvalidCredentialsException;
import com.aura.godeliver.mapper.UserMapper;
import com.aura.godeliver.repository.UserRepository;
import com.aura.godeliver.security.JwtService;
import com.aura.godeliver.service.AuthService;
import com.aura.godeliver.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public RegisterResponseDto register(RegisterRequestDto request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException();
        }

        User user = userMapper.toEntity(request);

        user.setPasswordHash(
                passwordEncoder.encode(request.password()));

        User savedUser = userRepository.save(user);

        return userMapper.toRegisterResponse(savedUser);
    }

    @Override
    public LoginResult login(LoginRequestDto request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtService.generateAccessToken(user);

        RefreshTokenResult refreshTokenResult = refreshTokenService.create(user.getId());

        LoginResponseDto response = new LoginResponseDto(
                accessToken,
                "Bearer",
                jwtService.getAccessTokenExpirationSeconds());

        return new LoginResult(
                response,
                refreshTokenResult.rawToken());
    }

    @Override
    public LoginResponseDto refresh(String rawRefreshToken) {

        RefreshToken refreshToken = refreshTokenService.verify(rawRefreshToken);

        User user = userRepository.findById(
                refreshToken.getUserId()).orElseThrow(InvalidCredentialsException::new);

        String accessToken = jwtService.generateAccessToken(user);

        return new LoginResponseDto(
                accessToken,
                "Bearer",
                jwtService.getAccessTokenExpirationSeconds());
    }

    @Override
    public void logout(String rawRefreshToken) {

        RefreshToken refreshToken = refreshTokenService.verify(rawRefreshToken);

        refreshTokenService.revoke(refreshToken);
    }
}