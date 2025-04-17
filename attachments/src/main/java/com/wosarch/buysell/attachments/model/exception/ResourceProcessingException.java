package com.wosarch.buysell.attachments.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceProcessingException extends RuntimeException {

    public ResourceProcessingException(ExceptionResources resourceName, String resourceId, String message) {
        super(String.format("%s can not be processed. Id %s, message: %s", resourceName.name(), resourceId, message));
    }
}
