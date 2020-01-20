package com.yura.repairservice.service;

import com.yura.repairservice.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void register(User user);

    List<User> findAll(Pageable pageable);

    long numberOfEntries();
}
