package com.stockportfolio.service;

public interface EmailServiceInterface {
    void sendAlertMail(String to, String subject, String body);
}
