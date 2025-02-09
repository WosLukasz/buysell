package com.wosarch.buysell.admin.services.dictionaries;

import com.wosarch.buysell.admin.model.dictionaries.Dictionary;
import com.wosarch.buysell.admin.model.dictionaries.DictionaryBuilder;
import com.wosarch.buysell.admin.model.dictionaries.DictionaryElement;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Map;

public class DictionaryBuilderImpl implements DictionaryBuilder {

    Dictionary dictionary;

    @Override
    public DictionaryBuilder create(String name) {
        dictionary = new Dictionary();
        dictionary.setCode(name);

        return this;
    }

    @Override
    public DictionaryBuilder load(Dictionary dictionary) {
        this.dictionary = dictionary;

        return this;
    }

    @Override
    public DictionaryBuilder setValueType(String valueType) {
        dictionary.setValueType(valueType);

        return this;
    }

    @Override
    public DictionaryBuilder addElement(String name, Object value) {
        if (CollectionUtils.isEmpty(dictionary.getElements())) {
            dictionary.setElements(new ArrayList<>());
        }

        DictionaryElement dictionaryElement = DictionaryElement.builder()
                .code(name)
                .value(value)
                .build();

        dictionary.getElements().add(dictionaryElement);

        return this;
    }

    @Override
    public DictionaryBuilder addElement(String name, Object value, Map<String, Object> properties) {
        if (CollectionUtils.isEmpty(dictionary.getElements())) {
            dictionary.setElements(new ArrayList<>());
        }

        DictionaryElement dictionaryElement = DictionaryElement.builder()
                .code(name)
                .value(value)
                .properties(properties)
                .build();

        dictionary.getElements().add(dictionaryElement);

        return this;
    }

    @Override
    public DictionaryBuilder removeElement(String name) {
        if (CollectionUtils.isEmpty(dictionary.getElements())) {
            dictionary.setElements(new ArrayList<>());
        }

        dictionary.getElements()
                .removeIf(dictionaryElement -> dictionaryElement.getCode().equals(name));

        return this;
    }

    @Override
    public Dictionary build() {
        return dictionary;
    }
}
