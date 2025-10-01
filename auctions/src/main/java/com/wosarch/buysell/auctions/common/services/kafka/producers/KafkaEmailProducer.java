package com.wosarch.buysell.auctions.common.services.kafka.producers;

import com.wosarch.buysell.auctions.common.model.emails.EmailData;
import com.wosarch.buysell.auctions.common.model.kafka.KafkaTopics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaEmailProducer {

    Logger logger = LoggerFactory.getLogger(KafkaEmailProducer.class);

    @Autowired
    private KafkaTemplate<String, EmailData> kafkaEmailTemplate;

    public void sendMessage(String recipient, EmailData data) {
        CompletableFuture<SendResult<String, EmailData>> future = kafkaEmailTemplate.send(KafkaTopics.EMAILS_QUEUE.name(), recipient, data);
        future.whenComplete((result, exception) -> {
            if (exception == null) {
                logger.info("Email to {} with subject {} sent.", recipient, data.getSubject(), exception);
            } else {
                logger.error("Error sending email to {} with subject {}", recipient, data.getSubject(), exception);
            }
        });
    }

}
