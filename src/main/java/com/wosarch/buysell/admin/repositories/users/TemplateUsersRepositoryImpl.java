package com.wosarch.buysell.admin.repositories.users;

import com.wosarch.buysell.admin.repositories.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateUsersRepositoryImpl extends AdminRepository implements TemplateUsersRepository {

    Logger logger = LoggerFactory.getLogger(TemplateUsersRepositoryImpl.class);
}
