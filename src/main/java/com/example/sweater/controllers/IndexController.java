package com.example.sweater.controllers;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class IndexController {

    public static final String INDEX = "/index";
    public static final String MAIN = "main";
    public static final String MESSAGES_ATTRIBUTE_NAME = "messages";
    private final MessageRepository messageRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public IndexController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping(INDEX)
    public String index(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        if (filter != null && !filter.isEmpty()) {
            model.addAttribute(MESSAGES_ATTRIBUTE_NAME, messageRepository.findAllByTextContainingIgnoreCase(filter));
        } else {
            model.addAttribute(MESSAGES_ATTRIBUTE_NAME, messageRepository.findAll());
        }
        model.addAttribute("filter", filter);
        return MAIN;
    }

    @PostMapping(INDEX)
    public String addMessage(@AuthenticationPrincipal User user,
                             @RequestParam String text,
                             @RequestParam String tag, Model model,
                             @RequestParam("file") MultipartFile file) throws IOException {
        final Message message = Message.builder().text(text).tag(tag).author(user).build();
        saveFile(file, message);
        messageRepository.save(message);
        model.addAttribute(MESSAGES_ATTRIBUTE_NAME, messageRepository.findAll());
        return MAIN;
    }

    private void saveFile(MultipartFile file, Message message) throws IOException {
        if (!file.isEmpty()) {
            final File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            final String uuidFile = UUID.randomUUID().toString();
            final String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            message.setFilename(resultFileName);
        }
    }
}
