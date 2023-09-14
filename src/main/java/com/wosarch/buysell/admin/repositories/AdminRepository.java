package com.wosarch.buysell.admin.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRepository {

    @Autowired
    @Qualifier("adminMongoTemplate")
    protected MongoTemplate mongoTemplate;
}
