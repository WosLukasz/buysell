package com.wosarch.buysell.admin.model.dictionaries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(Dictionary.COLLECTION_NAME)
public class Dictionary {

    @Transient
    public static final String COLLECTION_NAME = "dictionaries";

    @Id
    private String code;

    private String valueType;

    private List<DictionaryElement> elements;


    @UtilityClass
    public static class DictionaryElementValueTypes {
        public static final String STRING = "STRING";
        public static final String BOOLEAN = "BOOLEAN";
    }
}
