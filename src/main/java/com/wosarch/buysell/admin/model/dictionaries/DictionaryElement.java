package com.wosarch.buysell.admin.model.dictionaries;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.UtilityClass;

import java.util.Map;

@Data
@Builder
public class DictionaryElement {

    private String code;

    private Object value;

    private Map<String, Object> properties;
}
