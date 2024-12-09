package com.scm.service;

public interface EmailService {

    void sendEmail(String to,String subject,String body);

    void sendWithEmail();

    void sendEmailWithAttachment();
}
