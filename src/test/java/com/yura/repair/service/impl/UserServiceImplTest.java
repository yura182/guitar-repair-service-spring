package com.yura.repair.service.impl;

import com.yura.repair.dto.UserDto;
import com.yura.repair.entity.Role;
import com.yura.repair.entity.UserEntity;
import com.yura.repair.exception.AlreadyRegisteredUserException;
import com.yura.repair.repository.UserRepository;
import com.yura.repair.service.mapper.EntityMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UserServiceImpl.class)
public class UserServiceImplTest {
    private static final UserDto USER_DTO = getUserDto();
    private static final UserEntity USER_ENTITY = getUserEntity();
    private static final String PASSWORD = "12345678";
    private static final String EMAIL = "yura@gmail.com";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EntityMapper<UserEntity, UserDto> userMapper;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserServiceImpl userService;

    @Test
    public void registerShouldRegisterUser() {
        when(userRepository.findByEmail(USER_DTO.getEmail())).thenReturn(Optional.empty());
        when(encoder.encode(USER_DTO.getPassword())).thenReturn(USER_DTO.getPassword());
        when(userMapper.mapDtoToEntity(USER_DTO)).thenReturn(USER_ENTITY);

        userService.register(USER_DTO);

        verify(userRepository).save(USER_ENTITY);
    }

    @Test
    public void registerShouldThrowAlreadyRegisteredException() {
        exception.expect(AlreadyRegisteredUserException.class);
        exception.expectMessage("User with such email already exist");

        when(userRepository.findByEmail(USER_DTO.getEmail())).thenReturn(Optional.of(USER_ENTITY));

        userService.register(USER_DTO);
    }


    @Test
    public void loadUserByUsernameLoginUserAndReturnLoggedUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(USER_ENTITY));
        when(userMapper.mapEntityToDto(USER_ENTITY)).thenReturn(USER_DTO);

        UserDto actual = (UserDto) userService.loadUserByUsername(anyString());

        assertEquals(USER_DTO, actual);
    }

    @Test
    public void loginShouldThrowUserNotFoundExceptionWithIncorrectEmail() {
        exception.expect(EntityNotFoundException.class);
        exception.expectMessage("User not found");

        when(userRepository.findByEmail("EMAIL")).thenReturn(Optional.empty());
        when(userMapper.mapEntityToDto(USER_ENTITY)).thenReturn(USER_DTO);

        userService.loadUserByUsername(EMAIL);
    }

    @Test
    public void findAllShouldReturnPageOfUsers() {
        Page<UserDto> expected = new PageImpl<>(Collections.singletonList(USER_DTO));
        Page<UserEntity> userEntities = new PageImpl<>(Collections.singletonList(USER_ENTITY));
        Pageable pageable = PageRequest.of(1, 1);

        when(userRepository.findAll(pageable)).thenReturn(userEntities);
        when(userMapper.mapEntityToDto(USER_ENTITY)).thenReturn(USER_DTO);

        Page<UserDto> actual = userService.findAll(pageable);

        assertEquals(expected, actual);
    }

    @Test
    public void findAllShouldReturnEmptyPage() {
        Page<UserDto> expected = Page.empty();
        ;
        Page<UserEntity> userEntities = Page.empty();
        Pageable pageable = PageRequest.of(1, 1);

        when(userRepository.findAll(pageable)).thenReturn(userEntities);
        when(userMapper.mapEntityToDto(USER_ENTITY)).thenReturn(USER_DTO);

        Page<UserDto> actual = userService.findAll(pageable);

        assertEquals(expected, actual);
    }

    private static UserDto getUserDto() {
        return UserDto.builder()
                .name("Yura")
                .surname("Petrashenko")
                .email(EMAIL)
                .password(PASSWORD)
                .phone("0665005050")
                .role(Role.CLIENT)
                .build();
    }

    private static UserEntity getUserEntity() {
        return UserEntity.builder()
                .id(1)
                .name("Yura")
                .surname("Petrashenko")
                .email(EMAIL)
                .password(PASSWORD)
                .phone("0665005050")
                .role(Role.CLIENT)
                .build();
    }
}