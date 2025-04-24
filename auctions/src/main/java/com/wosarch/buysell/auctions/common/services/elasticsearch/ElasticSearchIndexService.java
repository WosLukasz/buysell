package com.wosarch.buysell.auctions.common.services.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
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

        final List<Class<?>> elasticSearchDocuments = elasticSearchDocumentsService.getElasticSearchDocumentsClasses(DEFAULT_BASE_PACKAGE);
        for (Class<?> clazz : elasticSearchDocuments) {
            upsertIndex(clazz);
        }

        logger.info("Finised upserting all indexes ...");
    }

    public void upsertIndex(Class<?> clazz) {
        logger.info("Upserting index for class {} ...", clazz.getName());

        String indexName = elasticSearchDocumentsService.extractIndexName(clazz);
        String mappingPath = elasticSearchDocumentsService.extractMappingPath(clazz);
        String settingsPath = elasticSearchDocumentsService.extractSettingsPath(clazz);
        if (Objects.isNull(indexName) || Objects.isNull(mappingPath)) {
            logger.warn("Upserting index for class {} failed. Can not find indexName or mapping path ...", clazz.getName());
        }

        upsertIndex(indexName, settingsPath, mappingPath);
    }

    public void upsertIndex(String indexName, String settingsPath, String mappingPath) {
        logger.info("Upserting index {} ...", indexName);

        try {
            final BooleanResponse indexExists = elasticsearchClient.indices().exists(r -> r.index(indexName));
            if (indexExists.value()) {
                logger.info("Index {} already exists. Removing...", indexName);
                elasticsearchClient.indices().delete(d -> d.index(indexName)); // Removing all data here. Change it to moving index in the future.
            }

            logger.info("Creating new index {} ...", indexName);
            elasticsearchClient.indices().create(buildCreateIndexRequest(indexName, settingsPath, mappingPath));
        } catch (IOException e) {
            logger.warn("Error during {} index creating", indexName);
            throw new RuntimeException(e);
        }

        logger.info("Finised upserting index {} ...", indexName);
    }

    private CreateIndexRequest buildCreateIndexRequest(String indexName, String settingsPath, String mappingPath) {
        return new CreateIndexRequest.Builder()
                .index(indexName)
                .settings(buildSettings(settingsPath))
                .mappings(buildMappings(mappingPath))
                .build();
    }

    private IndexSettings buildSettings(String mappingPath) {
        return new IndexSettings.Builder()
                .withJson(mappingService.read(mappingPath))
                .build();
    }

    private TypeMapping buildMappings(String mappingPath) {
        return new TypeMapping.Builder()
                .withJson(mappingService.read(mappingPath))
                .build();
    }
}
