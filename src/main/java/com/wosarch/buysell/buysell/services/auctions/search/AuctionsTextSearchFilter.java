package com.wosarch.buysell.buysell.services.auctions.search;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import com.wosarch.buysell.buysell.model.auctions.search.AuctionsSearchFilter;
import com.wosarch.buysell.buysell.model.auctions.search.AuctionsSearchRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuctionsTextSearchFilter implements AuctionsSearchFilter {

    @Override
    public Optional<Query> getQuery(AuctionsSearchRequest request) {
        if (StringUtils.isEmpty(request.getText())) {
            return Optional.empty();
        }

        QueryVariant matchQueryFirst = new MatchQuery.Builder()
                .field("title")
                .field("description")
                .query(request.getText())
                .build();

        return Optional.of(new Query(matchQueryFirst));
    }
}
