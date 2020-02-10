package com.yura.repair.service.mapper;

import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper implements EntityMapper<UserEntity, UserDto> {
    @Override
    public UserDto mapEntityToDto(UserEntity entity) {
        return Objects.isNull(entity) ? null : UserDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .phone(entity.getPhone())
                .role(entity.getRole())
                .build();
    }

    @Override
    public UserEntity mapDtoToEntity(UserDto dto) {
        return Objects.isNull(dto) ? null : UserEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .surname(dto.getSurname())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phone(dto.getPhone())
                .role(dto.getRole())
                .build();
    }
}
