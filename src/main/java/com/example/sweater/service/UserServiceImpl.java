package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final MailSenderService mailSenderService;

    public UserServiceImpl(UserRepository userRepository, MailSenderService mailSenderService) {
        this.userRepository = userRepository;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " was not found");
        } else {
            return user;
        }
    }

    public boolean addUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return false;
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        sendMessage(user);
        return true;
    }

    private void sendMessage(User user) {
        if (!user.getEmail().isEmpty()) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSenderService.send(user.getEmail(), "Activation code", message);
        }
    }

    @Override
    public boolean activateUser(String code) {
        final Optional<User> optionalUser = userRepository.findByActivationCode(code);

        if(optionalUser.isEmpty()) {
            return false;
        } else  {
            final User user = optionalUser.get();
            user.setActivationCode(null);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public List<User> findAll() {
        return StreamSupport
                .stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);

        final Set<String> roles = Arrays
                .stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for(String key : form.keySet()) {
            if(roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepository.save(user);
    }

    @Override
    public void updateProfile(User user, String email, String password) {
        final String userEmail = user.getEmail();
        final boolean emailHasChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));
        if(emailHasChanged) {
            user.setEmail(email);
            if(!email.isEmpty()) user.setActivationCode(UUID.randomUUID().toString());
        }
        if(!password.isEmpty()) user.setPassword(password);
        userRepository.save(user);
        if(emailHasChanged) sendMessage(user);
    }
}
