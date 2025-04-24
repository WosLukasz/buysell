package com.wosarch.buysell.auctions.buysell.services.auctions;

import com.wosarch.buysell.auctions.common.services.sequence.SequenceServiceImpl;
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
    private MongoTemplate mongoTemplate;

    @Override
    protected MongoTemplate getProperMongoTemplate() {
        return mongoTemplate;
    }
}