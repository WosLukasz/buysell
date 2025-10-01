package com.wosarch.buysell.emails.kafka.consumers;

import com.wosarch.buysell.emails.model.emails.EmailData;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @KafkaListener(topics = "EMAILS_QUEUE", containerFactory = "kafkaEmailListenerContainerFactory")
    public void emailsConsumer(@Payload EmailData emailData,
                               @Header(name = KafkaHeaders.RECEIVED_KEY) String recipient) {
        System.out.println("Email sending to: " + recipient);
    }
}
