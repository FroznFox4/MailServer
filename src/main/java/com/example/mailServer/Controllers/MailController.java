package com.example.mailServer.Controllers;

import com.example.mailServer.Services.MailService.MailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class MailController {
    @Autowired
    MailService mailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody Map<String, Collection<String>> emails ) {
        mailService.sendMail(emails.getOrDefault("emails", List.of() ));
        return ResponseEntity.ok().body("Email sent");
    }
}
