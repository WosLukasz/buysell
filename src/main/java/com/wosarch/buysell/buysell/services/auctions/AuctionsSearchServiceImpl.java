package com.wosarch.buysell.buysell.services.auctions;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.AuctionsSearchService;
import com.wosarch.buysell.buysell.model.auctions.search.AuctionsSearchFilter;
import com.wosarch.buysell.buysell.model.auctions.search.AuctionsSearchRequest;
import com.wosarch.buysell.buysell.model.auctions.search.AuctionsSearchResponse;
import com.wosarch.buysell.common.services.elasticsearch.ElasticSearchSearchService;
import com.wosarch.buysell.common.services.elasticsearch.ElasticSearchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionsSearchServiceImpl implements AuctionsSearchService {

    @Autowired
    private ElasticSearchSearchService searchService;

    @Autowired
    private List<AuctionsSearchFilter> auctionsSearchFilters;

    @Override
    public AuctionsSearchResponse search(AuctionsSearchRequest request) {
        Query searchQuery = prepareSearchQuery(request);
        SearchRequest searchRequest = ElasticSearchUtils.buildSearchRequest(Auction.COLLECTION_NAME, searchQuery, request.getOffset(), request.getPageSize());
        SearchResponse<Auction> auctions = searchService.search(searchRequest, Auction.class);
        AuctionsSearchResponse response = new AuctionsSearchResponse();
        response.setAuctions(ElasticSearchUtils.getObjectsList(auctions));
        response.setTotal(ElasticSearchUtils.getTotal(auctions));

        return response;
    }

    private Query prepareSearchQuery(AuctionsSearchRequest request) {
        BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();
        auctionsSearchFilters.forEach(auctionsSearchFilter ->
                    auctionsSearchFilter.getQuery(request).ifPresent(boolQueryBuilder::must));

        return new Query(boolQueryBuilder.build());
    }
}
