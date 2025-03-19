package com.wosarch.buysell.buysell.model.common;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.experimental.UtilityClass;

@Data
@Embeddable
public class Amount {

    private Double value;

    private String currency;

    @UtilityClass
    public static class Fields {
        public static final String VALUE = "value";
        public static final String CURRENCY = "currency";
    }

}
