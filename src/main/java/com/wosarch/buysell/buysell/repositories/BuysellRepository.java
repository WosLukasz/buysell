package com.wosarch.buysell.buysell.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BuysellRepository {

    @Autowired
    protected MongoTemplate mongoTemplate;
}
