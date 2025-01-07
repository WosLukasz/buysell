package com.wosarch.buysell.admin.model.dictionaries;

import java.util.Map;

public interface DictionaryBuilder {

    DictionaryBuilder create(String name);

    DictionaryBuilder load(Dictionary dictionary);

    DictionaryBuilder setValueType(String valueType);

    DictionaryBuilder addElement(String name, Object value);

    DictionaryBuilder addElement(String name, Object value, Map<String, Object> properties);

    DictionaryBuilder removeElement(String name);

    Dictionary build();
}
