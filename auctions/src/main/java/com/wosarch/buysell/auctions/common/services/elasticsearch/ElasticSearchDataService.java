package com.wosarch.buysell.auctions.common.services.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.wosarch.buysell.auctions.buysell.model.common.MongoObject;
import com.wosarch.buysell.auctions.common.model.mongo.MongoDataCollectorConfig;
import com.wosarch.buysell.auctions.common.services.mongo.MongoDataCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
public class ElasticSearchDataService {

    Logger logger = LoggerFactory.getLogger(ElasticSearchDataService.class);

    private static final String DEFAULT_BASE_PACKAGE = "com.wosarch.buysell";
    private static final Integer BATCH_SIZE = 1000;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ElasticSearchDocumentsService elasticSearchDocumentsService;

    public void reloadData() throws Exception {
        logger.info("Reloading data START ...");

        final List<Class<?>> elasticSearchDocuments = elasticSearchDocumentsService.getElasticSearchDocumentsClasses(DEFAULT_BASE_PACKAGE);
        for (Class<?> clazz : elasticSearchDocuments) {
            reloadIndex(clazz);
        }

        logger.info("Reloading data FINISHED ...");
    }

    public void reloadIndex(Class<?> clazz) throws Exception {
        logger.info("Reloading index for class {} ...", clazz.getName());

        String indexName = elasticSearchDocumentsService.extractIndexName(clazz);
        if (Objects.isNull(indexName)) {
            logger.warn("Reloading index for class {} failed. Can not find indexName...", clazz.getName());
        }

        reloadIndex(clazz, indexName);
    }

    private void reloadIndex(Class<?> clazz, String indexName) throws Exception {
        MongoDataCollectorConfig config = MongoDataCollectorConfig.builder()
                .mongoTemplate(mongoTemplate)
                .clazz(clazz)
                .filter(new Criteria())
                .pageSize(BATCH_SIZE)
                .build();

        MongoDataCollector collector = new MongoDataCollector(config);
        List<Object> dataToProcess = collector.getData();
        if (CollectionUtils.isEmpty(dataToProcess)) {
            logger.info("No data to reload.");
            return;
        }

        BulkRequest.Builder br = new BulkRequest.Builder();
        for (Object object : dataToProcess) {
            br.operations(operation -> operation
                    .index(idx -> idx
                            .index(indexName)
                            .id(MongoObject.Fields.ELASTICSEARCH_OBJECT_ID)
                            .document(object)
                    )
            );
        }

        BulkResponse result = elasticsearchClient.bulk(br.build());

        if (result.errors()) {
            logger.error("Reloading error in some bulk:");
            for (BulkResponseItem item: result.items()) {
                if (item.error() != null) {
                    logger.error(item.error().reason());
                }
            }
        }
    }
}
