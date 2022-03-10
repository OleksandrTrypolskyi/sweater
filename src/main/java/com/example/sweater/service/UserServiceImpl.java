package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

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

        if (!user.getEmail().isEmpty()) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSenderService.send(user.getEmail(), "Activation code", message);
        }
        return true;
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
}
