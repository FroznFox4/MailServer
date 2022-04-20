package com.example.mailServer.Services.MailService;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.el.lang.FunctionMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public Collection<Boolean> sendMails(Collection<String> collection) {
        return sendMails(collection.toArray(String[]::new));
    }

    @Override
    public Collection<Boolean> sendMails(String[] collection) {
        return Arrays.stream(collection).map(this::sendMail).collect(Collectors.toList());
    }

    @Override
    public boolean sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("UrGPU");
        message.setTo(email);
        message.setSubject("TestMessage");
        message.setText("Test");
        try {
            javaMailSender.send(message);
        } catch (MailException exception) {
            log.info(String.format("email %s failed", email));
            log.info(exception.getMessage());
            log.info(Arrays.toString(exception.getStackTrace()));
            return false;
        }
        return true;
    }

    @Override
    public Collection<Map<String, Boolean>> sendMailsAsync(Collection<String> collection) {
        List<CompletableFuture<Map<String, Boolean>>> mails =
                collection
                        .stream()
                        .map(this::sendMailAsync)
                        .collect(Collectors.toList());
        CompletableFuture<Void> allFuture = CompletableFuture
                .allOf(mails.toArray(new CompletableFuture[0]));
        CompletableFuture<List<Map<String, Boolean>>> allMailsContent =
                allFuture
                        .thenApply(v -> mails
                                .stream()
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList())
                        );
        return allMailsContent.join();
    }

    @Async
    private CompletableFuture<Map<String, Boolean>> sendMailAsync(String str) {
        log.info(String.format("email %s started", str));
        return CompletableFuture.supplyAsync(() -> Map.of(str, sendMail(str))).exceptionally(el -> {
            log.info(String.format("email %s failed", str));
            return Map.of(str, false);
        });
    }
}
