package com.wosarch.buysell.buysell.repositories.mongo.historicalauctions;

import com.wosarch.buysell.buysell.model.auctions.HistoricalAuction;

public interface TemplateHistoricalAuctionsRepository {

    HistoricalAuction daoSave(HistoricalAuction auction);
}
