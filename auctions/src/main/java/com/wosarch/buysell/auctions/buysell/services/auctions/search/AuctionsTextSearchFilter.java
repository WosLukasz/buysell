package com.wosarch.buysell.auctions.buysell.services.auctions.search;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryVariant;
import com.wosarch.buysell.auctions.buysell.model.auctions.Auction;
import com.wosarch.buysell.auctions.buysell.model.auctions.search.AuctionsSearchFilter;
import com.wosarch.buysell.auctions.buysell.model.auctions.search.AuctionsSearchRequest;
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

        QueryVariant titleMatchQueryFirst = new MatchQuery.Builder()
                .field(Auction.Fields.TITLE)
                .query(request.getText())
                .fuzziness("AUTO")
                .boost(3F)
                .build();

        QueryVariant descriptionMatchQueryFirst = new MatchQuery.Builder()
                .field(Auction.Fields.DESCRIPTION)
                .query(request.getText())
                .fuzziness("AUTO")
                .build();

        BoolQuery boolQuery = new BoolQuery.Builder()
                .should(new Query(titleMatchQueryFirst), new Query(descriptionMatchQueryFirst))
                .minimumShouldMatch("1")
                .build();

        return Optional.of(new Query(boolQuery));
    }
}
