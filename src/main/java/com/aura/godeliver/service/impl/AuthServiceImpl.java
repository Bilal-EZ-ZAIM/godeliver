package com.aura.godeliver.service.impl;

import com.aura.godeliver.dto.RegisterRequestDto;
import com.aura.godeliver.dto.RegisterResponseDto;
import com.aura.godeliver.entity.User;
import com.aura.godeliver.exception.EmailAlreadyExistsException;
import com.aura.godeliver.mapper.UserMapper;
import com.aura.godeliver.repository.UserRepository;
import com.aura.godeliver.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

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
}