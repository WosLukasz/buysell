package com.wosarch.buysell.auctions.buysell.services.auctions.search;

import co.elastic.clients.elasticsearch._types.query_dsl.NestedQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.json.JsonData;
import com.wosarch.buysell.auctions.buysell.model.auctions.Auction;
import com.wosarch.buysell.auctions.buysell.model.auctions.search.AuctionsSearchFilter;
import com.wosarch.buysell.auctions.buysell.model.auctions.search.AuctionsSearchRequest;
import com.wosarch.buysell.auctions.buysell.model.common.Amount;
import com.wosarch.buysell.auctions.common.services.elasticsearch.ElasticSearchUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuctionsPriceSearchFilter implements AuctionsSearchFilter {

    @Override
    public Optional<Query> getQuery(AuctionsSearchRequest request) {
        if (Objects.isNull(request.getPriceFrom()) && Objects.isNull(request.getPriceTo())) {
            return Optional.empty();
        }

        JsonData minValue = Objects.isNull(request.getPriceFrom()) ? null : JsonData.of(request.getPriceFrom());
        JsonData maxValue = Objects.isNull(request.getPriceTo()) ? null : JsonData.of(request.getPriceTo());
        RangeQuery rangeQuery = new RangeQuery.Builder()
                .field(ElasticSearchUtils.dots(Auction.Fields.PRICE, Amount.Fields.VALUE))
                .gte(minValue)
                .lte(maxValue)
                .build();

        NestedQuery nestedQuery = new NestedQuery.Builder()
                .path(Auction.Fields.PRICE)
                .query(new Query(rangeQuery))
                .build();

        return Optional.of(new Query(nestedQuery));
    }
}
