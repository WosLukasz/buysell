package com.wosarch.buysell.auctions.common.services.mongo;

import com.wosarch.buysell.auctions.buysell.model.common.MongoObject;
import com.wosarch.buysell.auctions.common.model.mongo.MongoDataCollectorConfig;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

public class MongoDataCollector<T extends MongoObject> {

    Logger logger = LoggerFactory.getLogger(MongoDataCollector.class);

    private MongoTemplate mongoTemplate;

    private Class<T> clazz;

    @Getter
    private int fetchedDocuments = 0;

    private final int pageSize;

    @Getter
    private final long total;

    private final Criteria filter;

    private final Fields projection;

    private Criteria chunkFilter;

    public MongoDataCollector(MongoDataCollectorConfig config) {
        this.mongoTemplate = config.getMongoTemplate();
        this.clazz = config.getClazz();
        this.filter = config.getFilter();
        this.chunkFilter = config.getFilter();
        this.projection = config.getProjection();
        this.pageSize = config.getPageSize();
        this.total = getTotalDocuments();
    }

    public List<T> getData() {
        if (fetchedDocuments >= total) {
            fetchedDocuments = 0;
            return Collections.emptyList();
        }

        logger.info("[{}/{}] Fetching data...", fetchedDocuments, total);

        final Aggregation agg = prepareAggregation(chunkFilter, projection, pageSize);
        List<T> data = mongoTemplate.aggregate(agg, clazz, clazz).getMappedResults();
        chunkFilter = getFilter(getLastObjectId(data));
        fetchedDocuments += data.size();
        logger.info("[{}/{}] Data fetched.", fetchedDocuments, total);

        return data;
    }

    private Aggregation prepareAggregation(Criteria chunkFilter, Fields projection, int pageSize) {
        if (Objects.nonNull(projection)) {
            return newAggregation(
                    match(chunkFilter),
                    project(projection),
                    limit(pageSize),
                    sort(Sort.by(Sort.Direction.ASC, MongoObject.Fields.OBJECT_ID))
            );
        }

        return newAggregation(
                match(chunkFilter),
                limit(pageSize),
                sort(Sort.by(Sort.Direction.ASC, MongoObject.Fields.OBJECT_ID))
        );
    }

    private long getTotalDocuments() {
        return mongoTemplate.count(Query.query(filter), clazz);
    }

    private Criteria getFilter(String lastObjectId) {
        return Objects.isNull(lastObjectId) ? filter : new Criteria().andOperator(
                Criteria.where(MongoObject.Fields.OBJECT_ID).gt(lastObjectId),
                filter
        );
    }

    private String getLastObjectId(List<T> data) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }

        T document = data.getLast();

        return document.getMongoId();
    }
}
