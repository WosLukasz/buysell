package com.wosarch.buysell.buysell.repositories.mongo.auctions;

import com.wosarch.buysell.buysell.model.auctions.Auction;

public interface TemplateAuctionsRepository {

    Auction daoSave(Auction auction);
}
