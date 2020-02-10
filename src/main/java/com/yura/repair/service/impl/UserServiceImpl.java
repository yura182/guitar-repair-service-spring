package com.yura.repair.service.impl;

import com.yura.repair.dto.UserDto;
import com.yura.repair.exception.AlreadyRegisteredUserException;
import com.yura.repair.repository.UserRepository;
import com.yura.repair.service.UserService;
import com.yura.repair.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void register(UserDto userDto) {
        userRepository.findByEmail(userDto.getEmail()).ifPresent(u -> {
            log.warn("User with such email already exist " + u.getEmail());
            throw new AlreadyRegisteredUserException("User with such email already exist " + u.getEmail());
        });

        userDto.setPassword(encoder.encode(userDto.getPassword()));
        userRepository.save(userMapper.mapUserToUserEntity(userDto));
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository
                .findAll(pageable)
                .map(userMapper::mapUserEntityToUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository
                .findByEmail(email)
                .map(userMapper::mapUserEntityToUser)
                .orElseThrow(() -> new EntityNotFoundException("User not found with provided email " + email));
    }

    @Override
    public long numberOfEntries() {
        return userRepository.count();
    }
}
