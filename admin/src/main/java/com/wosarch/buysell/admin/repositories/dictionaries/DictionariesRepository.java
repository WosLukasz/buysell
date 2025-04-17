package com.wosarch.buysell.admin.repositories.dictionaries;

import com.wosarch.buysell.admin.model.dictionaries.Dictionary;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface DictionariesRepository extends CrudRepository<Dictionary, Long>, TemplateDictionariesRepository {

}
