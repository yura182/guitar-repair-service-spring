package com.yura.repair.service.mapper;

import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.Role;
import com.yura.repair.entity.UserEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserMapperTest {
    private static final UserDto USER_DTO = getUserDto();
    private static final UserEntity USER_ENTITY = getUserEntity();

    private final EntityMapper<UserEntity, UserDto> userMapper = new UserMapper();

    @Test
    public void mapEntityToDtoShouldMapToDto() {
        UserDto actual = userMapper.mapEntityToDto(USER_ENTITY);

        assertEquals(USER_DTO, actual);
    }

    @Test
    public void mapDtoToEntityShouldMapToEntity() {
        UserEntity actual = userMapper.mapDtoToEntity(USER_DTO);

        assertEquals(USER_ENTITY, actual);
    }

    @Test
    public void mapDtoToEntityShouldReturnNull() {
        UserEntity actual = userMapper.mapDtoToEntity(null);

        assertNull(actual);
    }

    @Test
    public void mapEntityToDtoShouldReturnNull() {
        UserDto actual = userMapper.mapEntityToDto(null);

        assertNull(actual);
    }

    private static UserDto getUserDto() {
        return UserDto.builder()
                .id(1)
                .name("Yura")
                .surname("Petrashenko")
                .email("yura@gmail.com")
                .password("12345678")
                .phone("0665005050")
                .role(Role.CLIENT)
                .build();
    }

    private static UserEntity getUserEntity() {
        return UserEntity.builder()
                .id(1)
                .name("Yura")
                .surname("Petrashenko")
                .email("yura@gmail.com")
                .password("12345678")
                .phone("0665005050")
                .role(Role.CLIENT)
                .build();
    }
}