package com.wosarch.buysell.auctions.common.model.exception;

import jakarta.ws.rs.WebApplicationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BuysellException extends WebApplicationException {

    private String code;

    public BuysellException() {
        code = "";
    }

    public BuysellException(String message, String code) {
        super(message);
        this.code = code;
    }

    public BuysellException(String message) {
        super(message);
        this.code = "";
    }

    public BuysellException(String message, Throwable throwable) {
        super(message, throwable);
        this.code = "";
    }

    public BuysellException(Throwable throwable) {
        super(throwable);
        this.code = "";
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", super.toString(), code);
    }
}
