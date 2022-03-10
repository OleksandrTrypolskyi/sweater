package com.example.sweater.controllers;

import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    public static final String REGISTRATION_PAGE = "registrationPage";
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return REGISTRATION_PAGE;
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) {
        final boolean savingResult = userService.addUser(user);
        if (savingResult) {
            return "redirect:/login";
        } else {
            model.addAttribute("message", "User exists");
            return REGISTRATION_PAGE;
        }

    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code not found");
        }
        return "login";
    }
}
