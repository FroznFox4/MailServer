package com.example.mailServer.Services.MailService;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public void sendMail(Collection<String> collection) {
        sendMail(collection.toArray(String[]::new));
    }

    @Override
    public void sendMail(String[] collection) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("UrGPU");
        message.setTo(collection);
        message.setSubject("TestMessage");
        message.setText("Test");
        javaMailSender.send(message);
    }
}
