package com.wosarch.buysell.auctions.buysell.repositories.elastic.auctions;

import com.wosarch.buysell.auctions.buysell.model.auctions.Auction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionsElasticSearchRepository extends ElasticsearchRepository<Auction, String> {
}
