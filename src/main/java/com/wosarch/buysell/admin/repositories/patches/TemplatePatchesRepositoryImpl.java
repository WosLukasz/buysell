package com.wosarch.buysell.admin.repositories.patches;

import com.wosarch.buysell.admin.repositories.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TemplatePatchesRepositoryImpl extends AdminRepository implements TemplatePatchesRepository {

    Logger logger = LoggerFactory.getLogger(TemplatePatchesRepositoryImpl.class);

}
