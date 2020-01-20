package com.yura.repairservice.service.mapper;

import com.yura.repairservice.domain.user.User;
import com.yura.repairservice.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class UserMapper {
    public User mapUserEntityToUser(UserEntity userEntity) {
        return Objects.isNull(userEntity) ? null : User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .phone(userEntity.getPhone())
                .role(userEntity.getRole())
                .build();
    }

    public UserEntity mapUserToUserEntity(User user) {
        return Objects.isNull(user) ? null : UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(user.getPassword())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }
}
