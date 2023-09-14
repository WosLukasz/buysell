package com.wosarch.buysell.admin.services;

import com.wosarch.buysell.common.services.sequence.SequenceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@Qualifier("adminSequence")
public class AdminSequenceServiceImpl extends SequenceServiceImpl {

    @Autowired
    @Qualifier("adminMongoTemplate")
    private MongoTemplate adminMongoTemplate;

    @Override
    protected MongoTemplate getProperMongoTemplate() {
        return adminMongoTemplate;
    }
}
