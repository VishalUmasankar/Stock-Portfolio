package com.stockportfolio.controller;

import com.stockportfolio.service.EmailServiceInterface;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {

    private final EmailServiceInterface emailService;

    public MailController(EmailServiceInterface emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendMail(@RequestParam String to,
                           @RequestParam String subject,
                           @RequestParam String body) {
        emailService.sendAlertMail(to, subject, body);
        return "Mail sent successfully";
    }
}
