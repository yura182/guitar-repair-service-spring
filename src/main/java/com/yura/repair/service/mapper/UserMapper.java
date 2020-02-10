package com.yura.repair.service.mapper;

import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class UserMapper {
    public UserDto mapUserEntityToUser(UserEntity userEntity) {
        return Objects.isNull(userEntity) ? null : UserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .phone(userEntity.getPhone())
                .role(userEntity.getRole())
                .build();
    }

    public UserEntity mapUserToUserEntity(UserDto userDto) {
        return Objects.isNull(userDto) ? null : UserEntity.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phone(userDto.getPhone())
                .role(userDto.getRole())
                .build();
    }
}
