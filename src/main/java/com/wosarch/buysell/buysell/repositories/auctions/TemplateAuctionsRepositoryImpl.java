package com.wosarch.buysell.buysell.repositories.auctions;

import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.repositories.BuysellRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateAuctionsRepositoryImpl extends BuysellRepository implements TemplateAuctionsRepository {

    public Auction daoSave(Auction auction) {
        return versionedSave(auction, Auction.COLLECTION_NAME);
    }

}
