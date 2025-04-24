package com.wosarch.buysell.auctions.common.services.elasticsearch;

import co.elastic.clients.elasticsearch._types.*;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.wosarch.buysell.auctions.common.model.elasticsearch.ElasticSearchSearchRequest;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class ElasticSearchUtils {

    public static <T extends ElasticSearchSearchRequest> SearchRequest buildSearchRequest(final String indexName, final Query query, T searchRequest) {
        Integer offset = searchRequest.getOffset();
        if (Objects.isNull(offset)) {
            offset = 0;
        }

        return new SearchRequest.Builder()
                .index(indexName)
                .query(query)
                .from(offset)
                .size(searchRequest.getPageSize())
                .sort(prepareSort(searchRequest))
                .build();
    }

    public static <T> List<T> getObjectsList(SearchResponse<T> searchResponse) {
        if (Objects.isNull(searchResponse) || Objects.isNull(searchResponse.hits().total()) || searchResponse.hits().total().value() == 0) {
            return Collections.emptyList();
        }

        List<Hit<T>> hits = searchResponse.hits().hits();

        return hits.stream()
                .map(Hit::source)
                .toList();
    }

    public static <T> long getTotal(SearchResponse<T> searchResponse) {
        if (Objects.isNull(searchResponse) || Objects.isNull(searchResponse.hits().total())) {
            return 0L;
        }

        return searchResponse.hits().total().value();
    }

    public static String keywordField(String field) {
        return String.format("%s.keyword", field);
    }

    public static <T extends ElasticSearchSearchRequest> SortOptions prepareSort(T searchRequest) {
        if (Objects.isNull(searchRequest.getSortBy()) || Objects.isNull(searchRequest.getSortOrder())) {
            return new SortOptions.Builder()
                    .score(s -> s.order(SortOrder.Desc))
                    .build();
        }

        return new SortOptions.Builder()
                .field(f -> f.field(searchRequest.getSortBy()).order(searchRequest.getSortOrder()))
                .build();
    }

    public static String dots(String... fields) {
        return String.join(".", fields);
    }
}
