package com.wosarch.buysell.common.model.mongo;

import com.wosarch.buysell.buysell.model.common.MongoObject;
import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;

@Data
public class MongoDataCollectorConfig <T extends MongoObject> {

    private MongoTemplate mongoTemplate;

    private Class<T> clazz;

    private Criteria filter;

    private Fields projection;

    private int pageSize;

    private long total;
}
