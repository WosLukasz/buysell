package com.wosarch.buysell.emails.kafka.deserializers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wosarch.buysell.emails.model.emails.EmailData;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EmailDataDeserializer implements Deserializer<EmailData> {

    @Override
    public EmailData deserialize(String topic, byte[] data) {
        ObjectMapper objectMapper =  new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), EmailData.class);
        } catch (IOException e) {
            throw new RuntimeException("Deserialize error");
        }
    }
}
