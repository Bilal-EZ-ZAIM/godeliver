package com.aura.godeliver.mapper;

import com.aura.godeliver.dto.RegisterRequestDto;
import com.aura.godeliver.dto.RegisterResponseDto;
import com.aura.godeliver.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(RegisterRequestDto request);

    RegisterResponseDto toRegisterResponse(User user);
}