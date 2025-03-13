package com.wosarch.buysell.buysell.model.common;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = ContactInformation.ENTITY_NAME)
public class ContactInformation extends DatabaseObject {

    public static final String ENTITY_NAME = "contacts";

    @Enumerated(EnumType.STRING)
    private ContactInformationType type;

    private String value;
}
