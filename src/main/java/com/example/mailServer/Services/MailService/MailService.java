package com.example.mailServer.Services.MailService;

import java.util.Collection;
import java.util.List;

public interface MailService {
    void sendMail(Collection<String> collection);
    void sendMail(String[] collection);
}
