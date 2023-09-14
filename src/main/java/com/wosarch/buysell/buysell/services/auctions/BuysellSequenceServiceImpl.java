package com.wosarch.buysell.buysell.services.auctions;

import com.wosarch.buysell.common.services.sequence.SequenceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


@Primary
@Service
@Qualifier("buysellSequence")
public class BuysellSequenceServiceImpl extends SequenceServiceImpl {

    @Autowired
    private MongoTemplate adminMongoTemplate;

    @Override
    protected MongoTemplate getProperMongoTemplate() {
        return adminMongoTemplate;
    }
}