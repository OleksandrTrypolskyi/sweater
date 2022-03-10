package com.example.sweater.service;

public interface MailSenderService {
    void send(String emailTo, String subject, String message);
}
