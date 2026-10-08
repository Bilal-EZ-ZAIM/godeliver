package com.aura.godeliver.mapper;

import com.aura.godeliver.dto.auth.RegisterRequestDto;
import com.aura.godeliver.dto.auth.RegisterResponseDto;
import com.aura.godeliver.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(RegisterRequestDto request);

    RegisterResponseDto toRegisterResponse(User user);
}