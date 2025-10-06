package com.wosarch.buysell.emails.model.emails;

public interface EmailService {

    void sendSimpleMessage(String recipient, String subject, String message);

    void sendSimpleMessage(EmailData emailData);

}
