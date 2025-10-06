package com.wosarch.buysell.emails.kafka.consumers;

import com.wosarch.buysell.emails.model.emails.EmailData;
import com.wosarch.buysell.emails.model.emails.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "EMAILS_QUEUE", containerFactory = "kafkaEmailListenerContainerFactory")
    public void emailsConsumer(@Payload EmailData emailData,
                               @Header(name = KafkaHeaders.RECEIVED_KEY) String recipient) {
        emailService.sendSimpleMessage(emailData);
    }
}
