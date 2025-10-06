package com.wosarch.buysell.emails.services.emails;

import com.wosarch.buysell.emails.model.emails.EmailData;
import com.wosarch.buysell.emails.model.emails.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String recipient, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        emailSender.send(mailMessage);
    }

    @Override
    public void sendSimpleMessage(EmailData emailData) {
        sendSimpleMessage(emailData.getRecipient(), emailData.getSubject(), emailData.getMessage());
    }
}
