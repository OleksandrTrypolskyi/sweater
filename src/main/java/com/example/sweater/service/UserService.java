package com.example.sweater.service;

import com.example.sweater.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface UserService extends UserDetailsService {
    boolean addUser(User user);

    boolean activateUser(String code);

    List<User> findAll();

    User findById(Long userId);

    void saveUser(User user, String username, Map<String, String> form);

    void updateProfile(User user, String email, String password);
}
