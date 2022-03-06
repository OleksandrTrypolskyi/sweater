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

    public static final String INDEX = "/index";
    public static final String MAIN = "main";
    public static final String MESSAGES_ATTRIBUTE_NAME = "messages";
    private final MessageRepository messageRepository;

    public IndexController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping(INDEX)
    public String index(Model model) {
        model.addAttribute(MESSAGES_ATTRIBUTE_NAME, messageRepository.findAll());
        return MAIN;
    }

    @PostMapping(INDEX)
    public String addMessage(@RequestParam String text,
                             @RequestParam String tag, Model model) {
        messageRepository.save(Message.builder().text(text).tag(tag).build());
        model.addAttribute(MESSAGES_ATTRIBUTE_NAME, messageRepository.findAll());
        return MAIN;
    }

    @PostMapping("/filter")
    public String findMessages(@RequestParam String filter, Model model) {
        Iterable<Message> message;
        if(filter != null && !filter.isEmpty()) {
            model.addAttribute(MESSAGES_ATTRIBUTE_NAME, messageRepository.findAllByTextContainingIgnoreCase(filter));
        } else {
            model.addAttribute(MESSAGES_ATTRIBUTE_NAME, messageRepository.findAll());
        }

        return MAIN;
    }
}
