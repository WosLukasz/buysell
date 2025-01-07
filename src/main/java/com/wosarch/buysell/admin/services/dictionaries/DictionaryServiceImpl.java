package com.wosarch.buysell.admin.services.dictionaries;

import com.wosarch.buysell.admin.model.dictionaries.Dictionary;
import com.wosarch.buysell.admin.model.dictionaries.DictionaryService;
import com.wosarch.buysell.admin.repositories.dictionaries.DictionariesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionariesRepository dictionariesRepository;

    @Override
    public Dictionary saveDictionary(Dictionary dictionary) {
        return dictionariesRepository.save(dictionary);
    }

    @Override
    public void removeDictionary(String code) {
        dictionariesRepository.deleteById(code);
    }
}
