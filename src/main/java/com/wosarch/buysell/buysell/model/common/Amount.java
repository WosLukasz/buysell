package com.wosarch.buysell.buysell.model.common;

import lombok.Data;
import lombok.experimental.UtilityClass;

import java.io.Serializable;

@Data
public class Amount implements Serializable {

    private Double value;
    private String currency;

    @UtilityClass
    public static class Fields {
        public static final String VALUE = "value";
        public static final String CURRENCY = "currency";
    }

}
