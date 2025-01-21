package com.wosarch.buysell.common.services.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class ElasticSearchIndexService {

    Logger logger = LoggerFactory.getLogger(ElasticSearchIndexService.class);

    private static final String DEFAULT_BASE_PACKAGE = "com.wosarch.buysell";

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Autowired
    private ElasticSearchMappingService mappingService;

    @Autowired
    private ElasticSearchDocumentsService elasticSearchDocumentsService;

    public void upsertAllIndexes() {
        logger.info("Upserting all indexes ...");

        final Map<String, String> elasticSearchDocuments = elasticSearchDocumentsService.getElasticSearchDocumentsInfo(DEFAULT_BASE_PACKAGE);
        for (Map.Entry<String, String> document : elasticSearchDocuments.entrySet()) {
            final String indexName = document.getKey();
            final String mappingPath = document.getValue();

            upsertIndex(indexName, mappingPath);
        }

        logger.info("Finised upserting all indexes ...");
    }

    public void upsertIndex(Class<?> clazz) {
        logger.info("Upserting index for class {} ...", clazz.getName());

        String indexName = elasticSearchDocumentsService.extractIndexName(clazz);
        String mappingPath = elasticSearchDocumentsService.extractMappingPath(clazz);
        if (Objects.isNull(indexName) || Objects.isNull(mappingPath)) {
            logger.warn("Upserting index for class {} failed. Can not find indexName or mapping path ...", clazz.getName());
        }

        upsertIndex(indexName, mappingPath);
    }

    public void upsertIndex(String indexName, String mappingPath) {
        logger.info("Upserting index {} ...", indexName);

        try {
            final BooleanResponse indexExists = elasticsearchClient.indices().exists(r -> r.index(indexName));
            if (indexExists.value()) {
                logger.info("Index {} already exists. Removing...", indexName);
                elasticsearchClient.indices().delete(d -> d.index(indexName));
            }

            logger.info("Creating new index {} ...", indexName);
            elasticsearchClient.indices().create(buildCreateIndexRequest(indexName, mappingPath));
        } catch (IOException e) {
            logger.warn("Error during {} index creating", indexName);
            throw new RuntimeException(e);
        }

        logger.info("Finised upserting index {} ...", indexName);
    }

    private CreateIndexRequest buildCreateIndexRequest(String indexName, String mappingPath) {
        return new CreateIndexRequest.Builder()
                .index(indexName)
                .mappings(buildMappings(mappingPath))
                .build();
    }

    private TypeMapping buildMappings(String mappingPath) {
        return new TypeMapping.Builder()
                .withJson(mappingService.read(mappingPath))
                .build();
    }

}
