package com.wosarch.buysell.auctions.buysell.repositories.mongo.auctions;

import com.wosarch.buysell.auctions.buysell.model.auctions.Auction;
import com.wosarch.buysell.auctions.buysell.repositories.mongo.BuysellRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateAuctionsRepositoryImpl extends BuysellRepository implements TemplateAuctionsRepository {

    public Auction daoSave(Auction auction) {
        return versionedSave(auction, Auction.COLLECTION_NAME);
    }

}
