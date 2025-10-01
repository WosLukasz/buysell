package com.wosarch.buysell.auctions.common.services.kafka.config;

import com.wosarch.buysell.auctions.common.model.emails.EmailData;
import com.wosarch.buysell.auctions.common.model.kafka.KafkaTopics;
import com.wosarch.buysell.auctions.common.services.kafka.serializers.ObjectMapperSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaEmailProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, EmailData> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaTopics.EMAILS_QUEUE.name());
        configProps.put(ProducerConfig.ACKS_CONFIG, "-1");
        configProps.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, "1000");
//       configProps.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CreditCardTrnPartitioner.class);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ObjectMapperSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, EmailData> kafkaEmailTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}