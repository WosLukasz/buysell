package com.wosarch.buysell.buysell.services.auctions.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.search.AuctionsSearchFilter;
import com.wosarch.buysell.buysell.model.auctions.search.AuctionsSearchRequest;
import com.wosarch.buysell.common.services.elasticsearch.ElasticSearchUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuctionsCategorySearchFilter implements AuctionsSearchFilter {

    @Override
    public Optional<Query> getQuery(AuctionsSearchRequest request) {
        if (StringUtils.isEmpty(request.getCategory())) {
            return Optional.empty();
        }

        QueryVariant matchQueryFirst = new TermQuery.Builder()
                .field(ElasticSearchUtils.keywordField(Auction.Fields.CATEGORY))
                .value(request.getCategory())
                .build();

        return Optional.of(new Query(matchQueryFirst));
    }
}
