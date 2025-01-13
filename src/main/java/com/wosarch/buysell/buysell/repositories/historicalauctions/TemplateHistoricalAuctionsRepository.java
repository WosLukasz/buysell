package com.wosarch.buysell.buysell.repositories.historicalauctions;

import com.wosarch.buysell.buysell.model.auctions.HistoricalAuction;

public interface TemplateHistoricalAuctionsRepository {

    HistoricalAuction daoSave(HistoricalAuction auction);
}
