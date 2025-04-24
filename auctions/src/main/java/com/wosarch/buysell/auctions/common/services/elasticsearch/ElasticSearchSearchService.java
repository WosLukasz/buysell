package com.wosarch.buysell.auctions.common.services.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class ElasticSearchSearchService {

    Logger logger = LoggerFactory.getLogger(ElasticSearchSearchService.class);

    @Autowired
    private ElasticsearchClient client;

    public <T> SearchResponse<T> search(SearchRequest request, Class<T> clazz) {
        if (Objects.isNull(request)) {
            logger.error("Search request is empty");
            return null;
        }

        try {
            return client.search(request, clazz);
        } catch (IOException e) {
            logger.error("Error during search request", e);
            throw new RuntimeException(e);
        }
    }

}
