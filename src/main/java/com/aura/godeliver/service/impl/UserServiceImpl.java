package com.aura.godeliver.service.impl;

import com.aura.godeliver.dto.RegisterResponseDto;
import com.aura.godeliver.entity.User;
import com.aura.godeliver.exception.ResourceNotFoundException;
import com.aura.godeliver.mapper.UserMapper;
import com.aura.godeliver.repository.UserRepository;
import com.aura.godeliver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public RegisterResponseDto getCurrentUser(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(ResourceNotFoundException::new);

        return userMapper.toRegisterResponse(user);
    }
}