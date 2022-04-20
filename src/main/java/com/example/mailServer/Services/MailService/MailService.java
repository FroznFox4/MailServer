package com.example.mailServer.Services.MailService;

import java.util.Collection;
import java.util.Map;

public interface MailService {
    Collection<Boolean> sendMails(Collection<String> collection);
    Collection<Boolean> sendMails(String[] collection);
    boolean sendMail(String email);
    Collection<Map<String, Boolean>> sendMailsAsync(Collection<String> collection);
}
