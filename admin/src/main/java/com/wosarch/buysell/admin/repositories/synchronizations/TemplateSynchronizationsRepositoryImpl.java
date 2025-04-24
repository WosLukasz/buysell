package com.wosarch.buysell.admin.repositories.synchronizations;

import com.wosarch.buysell.admin.repositories.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateSynchronizationsRepositoryImpl extends AdminRepository implements TemplateSynchronizationsRepository {

    Logger logger = LoggerFactory.getLogger(TemplateSynchronizationsRepositoryImpl.class);

}
