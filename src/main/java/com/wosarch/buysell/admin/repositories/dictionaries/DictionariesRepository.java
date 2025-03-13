package com.wosarch.buysell.admin.repositories.dictionaries;

import com.wosarch.buysell.admin.model.dictionaries.Dictionary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictionariesRepository extends CrudRepository<Dictionary, Long>, TemplateDictionariesRepository {

}
