package com.example.sweater.controllers;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/usersList";
    }

    @GetMapping("/{userId}")
    public String userEditForm(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userRepository.findById(userId).get());
        model.addAttribute("roles", Role.values());
        return "users/userEdit";
    }

    @PostMapping
    public String editUser(@RequestParam("userId")  User user,
                           @RequestParam Map<String, String> form,
                           @RequestParam String username) {
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
        return "redirect:/users";
    }
}
