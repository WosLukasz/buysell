package com.wosarch.buysell.auctions.buysell.repositories.mongo.historicalauctions;

import com.wosarch.buysell.auctions.buysell.model.auctions.HistoricalAuction;

public interface TemplateHistoricalAuctionsRepository {

    HistoricalAuction daoSave(HistoricalAuction auction);
}
