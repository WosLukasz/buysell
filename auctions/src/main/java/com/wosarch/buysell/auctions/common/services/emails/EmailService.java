package com.wosarch.buysell.auctions.common.services.emails;

import com.wosarch.buysell.auctions.common.model.emails.EmailData;
import com.wosarch.buysell.auctions.common.services.kafka.producers.KafkaEmailProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private KafkaEmailProducer kafkaEmailProducer;

    public void send(String recipient, String subject, String message) {
        EmailData emailData = new EmailData();
        emailData.setRecipient(recipient);
        emailData.setSubject(subject);
        emailData.setMessage(message);

        kafkaEmailProducer.sendMessage(recipient, emailData);
    }

}
