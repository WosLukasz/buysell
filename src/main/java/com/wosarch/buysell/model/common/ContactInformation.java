package com.wosarch.buysell.model.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ContactInformation implements Serializable {
    private ContactInformationType type;
    private String value;
}
