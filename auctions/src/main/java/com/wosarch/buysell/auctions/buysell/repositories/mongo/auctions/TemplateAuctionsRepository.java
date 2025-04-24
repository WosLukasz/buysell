package com.wosarch.buysell.auctions.buysell.repositories.mongo.auctions;

import com.wosarch.buysell.auctions.buysell.model.auctions.Auction;

public interface TemplateAuctionsRepository {

    Auction daoSave(Auction auction);
}
