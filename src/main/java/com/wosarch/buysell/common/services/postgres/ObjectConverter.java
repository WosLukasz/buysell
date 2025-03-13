package com.wosarch.buysell.common.services.postgres;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ObjectConverter implements AttributeConverter<Object, String> {

    Logger logger = LoggerFactory.getLogger(ObjectConverter.class);

    @Override
    public String convertToDatabaseColumn(Object object) {

        String objectJson = null;
        try {
            objectJson = new ObjectMapper().writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }

        return objectJson;
    }

    @Override
    public Object convertToEntityAttribute(String objectJSON) {

        Object object = null;
        try {
            object = new ObjectMapper().readValue(objectJSON,
                    new TypeReference<Object>() {
                    });
        } catch (final IOException e) {
            logger.error("JSON reading error", e);
        }

        return object;
    }
}