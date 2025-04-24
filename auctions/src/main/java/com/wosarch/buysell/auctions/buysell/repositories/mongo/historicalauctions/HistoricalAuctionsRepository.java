package com.wosarch.buysell.auctions.buysell.repositories.mongo.historicalauctions;

import com.wosarch.buysell.auctions.buysell.model.auctions.HistoricalAuction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricalAuctionsRepository extends MongoRepository<HistoricalAuction, String>, TemplateHistoricalAuctionsRepository {

    default HistoricalAuction save(HistoricalAuction auction) {
        return daoSave(auction);
    }
}
