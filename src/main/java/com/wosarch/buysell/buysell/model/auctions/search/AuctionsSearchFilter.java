package com.wosarch.buysell.buysell.model.auctions.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.Optional;

public interface AuctionsSearchFilter {

    Optional<Query> getQuery(AuctionsSearchRequest request);
}
