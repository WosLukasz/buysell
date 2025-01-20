package com.wosarch.buysell.buysell.repositories.auctions.elastisearch;

import com.wosarch.buysell.buysell.model.auctions.Auction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AuctionsElasticSearchRepository extends ElasticsearchRepository<Auction, String> {
}
