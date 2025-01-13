package com.wosarch.buysell.common.model.mongo;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;

@Data
@Builder
public class MongoDataCollectorConfig {

    private MongoTemplate mongoTemplate;

    private Class clazz;

    private Criteria filter;

    private Fields projection;

    private int pageSize;
}
