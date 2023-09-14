package com.wosarch.buysell.common.model.sequence;

import lombok.Data;
import lombok.experimental.UtilityClass;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sequences")
public class Sequence {

    private String name;

    private long value;

    @UtilityClass
    public static class Fields {
        public static final String NAME = "name";
        public static final String VALUE = "value";

    }

}