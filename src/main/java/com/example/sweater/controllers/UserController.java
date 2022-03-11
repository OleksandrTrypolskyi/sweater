package com.example.sweater.controllers;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/usersList";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{userId}")
    public String userEditForm(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("roles", Role.values());
        return "users/userEdit";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String editUser(@RequestParam("userId")  User user,
                           @RequestParam Map<String, String> form,
                           @RequestParam String username) {
        userService.saveUser(user, username, form);
        return "redirect:/users";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam String email,
                                @RequestParam String password) {
        userService.updateProfile(user, email, password);
        return "redirect:/users/profile";
    }
}
