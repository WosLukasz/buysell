package com.wosarch.buysell.buysell.repositories.mongo.historicalauctions;

import com.wosarch.buysell.buysell.model.auctions.HistoricalAuction;
import com.wosarch.buysell.buysell.repositories.mongo.BuysellRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateHistoricalAuctionsRepositoryImpl extends BuysellRepository implements TemplateHistoricalAuctionsRepository {

    public HistoricalAuction daoSave(HistoricalAuction auction) {
        return versionedSave(auction, HistoricalAuction.COLLECTION_NAME);
    }

}
