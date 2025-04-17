package com.wosarch.buysell.attachments.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(ExceptionResources resourceName, String resourceId) {
        super(String.format("%s not found with the given id %s", resourceName.name(), resourceId));
    }
}
