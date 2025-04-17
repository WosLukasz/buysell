package com.wosarch.buysell.admin.repositories.dictionaries;

import com.wosarch.buysell.admin.repositories.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateDictionariesRepositoryImpl extends AdminRepository implements TemplateDictionariesRepository {

    Logger logger = LoggerFactory.getLogger(TemplateDictionariesRepositoryImpl.class);

}
