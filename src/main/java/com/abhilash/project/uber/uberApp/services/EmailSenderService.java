package com.abhilash.project.uber.uberApp.services;

public interface EmailSenderService {
    void sendEmail(String[] toEmail,String Subject,String body);
}
