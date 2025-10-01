package com.wosarch.buysell.auctions.common.services.kafka.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class ObjectMapperSerializer<T> implements Serializer<T> {
    @Override
    public byte[] serialize(String topic, T data) {
        ObjectMapper objectMapper =  new ObjectMapper();
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Serialize error");
        }

        return bytes;
    }
}
