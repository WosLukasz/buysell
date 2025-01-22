package com.wosarch.buysell.common.services.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.wosarch.buysell.buysell.model.common.MongoObject;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.SerializationUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class ElasticSearchUtils {

    public static SearchRequest buildSearchRequest(final String indexName, final Query query, Integer offset, final Integer pageSize) {
        if (Objects.isNull(offset)) {
            offset = 0;
        }

        return new SearchRequest.Builder()
                .index(indexName)
                .query(query)
                .from(offset)
                .size(pageSize)
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

    public static <T extends MongoObject> T prepareToSave(T object) {
        T copy = SerializationUtils.clone(object);
        copy.setMongoId(null);

        return copy;
    }
}
