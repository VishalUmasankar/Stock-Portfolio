package com.stockportfolio.controller;




import com.stockportfolio.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String triggerMail(@RequestBody String keyword) {
        if (keyword.equalsIgnoreCase("mail")) {
            emailService.sendAlertMail(
                "recipient-email@example.com", 
                "Stock Alert", 
                "The stock value has triggered an alert!"
            );
            return "Mail sent.";
        } else {
            return "No action taken.";
        }
    }
}
