package com.wosarch.buysell.auctions.common.model.emails;

import lombok.Data;

@Data
public class EmailData {
    private String recipient;
    private String subject;
    private String message;
}
