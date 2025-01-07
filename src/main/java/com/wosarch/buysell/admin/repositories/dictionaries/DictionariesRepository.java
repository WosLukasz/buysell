package com.wosarch.buysell.admin.repositories.dictionaries;

import com.wosarch.buysell.admin.model.dictionaries.Dictionary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionariesRepository extends MongoRepository<Dictionary, String>, TemplateDictionariesRepository {

}
