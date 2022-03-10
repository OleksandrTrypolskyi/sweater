package com.example.sweater.service;

import com.example.sweater.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    boolean addUser(User user);

    boolean activateUser(String code);
}
