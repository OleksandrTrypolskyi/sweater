package com.example.sweater.controllers;

import com.example.sweater.domain.Message;
import com.example.sweater.repositories.MessageRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    private final MessageRepository messageRepository;

    public IndexController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("messages", messageRepository.findAll());
        return "main";
    }

    @PostMapping("/")
    public String addMessage(@RequestParam String text,
                             @RequestParam String tag, Model model) {
        messageRepository.save(Message.builder().text(text).tag(tag).build());
        model.addAttribute("messages", messageRepository.findAll());
        return "main";
    }

    @PostMapping("/filter")
    public String findMessages(@RequestParam String filter, Model model) {
        Iterable<Message> message;
        if(filter != null && !filter.isEmpty()) {
            model.addAttribute("messages", messageRepository.findAllByTextContainingIgnoreCase(filter));
        } else {
            model.addAttribute("messages", messageRepository.findAll());
        }

        return "main";
    }
}
