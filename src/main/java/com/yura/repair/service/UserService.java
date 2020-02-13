package com.yura.repair.service;

import com.yura.repair.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void register(UserDto userDto);

    Page<UserDto> findAll(Pageable pageable);
}
