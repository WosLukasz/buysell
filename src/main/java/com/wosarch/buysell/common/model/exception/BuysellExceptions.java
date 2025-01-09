package com.wosarch.buysell.common.model.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class BuysellExceptions extends RuntimeException {

    private final List<Throwable> errors;

    public BuysellExceptions(String message, List<Throwable> errors) {
        super(message);
        this.errors = errors;
    }

    @Override
    public String toString() {
        if (CollectionUtils.isEmpty(errors)) {
            return "";
        }

        StringBuffer builder = new StringBuffer();
        builder.append(String.format("Message: %s%n", getMessage()));
        errors.forEach(error -> {
            builder.append(String.format("Error: %s: %s%n",
                    ExceptionUtils.getMessage(error),
                    ExceptionUtils.getStackTrace(error)));
        });

    }
}
