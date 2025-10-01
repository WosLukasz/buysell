package com.wosarch.buysell.emails.model.emails;

import lombok.Data;

@Data
public class EmailData {
    String recipient;
    String subject;
    String message;
}
