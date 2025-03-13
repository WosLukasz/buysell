package com.wosarch.buysell.buysell.model.common;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.UtilityClass;

@Data
@Entity
@Table(name = Amount.ENTITY_NAME)
public class Amount extends DatabaseObject {

    public static final String ENTITY_NAME = "amounts";

    private Double value;

    private String currency;

    @UtilityClass
    public static class Fields {
        public static final String VALUE = "value";
        public static final String CURRENCY = "currency";
    }

}
