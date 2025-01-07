package com.wosarch.buysell.admin.model.dictionaries;

public interface DictionaryService {

    Dictionary saveDictionary(Dictionary dictionary);

    void removeDictionary(String code);
}
