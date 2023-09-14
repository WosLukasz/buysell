package com.wosarch.buysell.buysell.repositories.auctions;

import com.mongodb.client.result.UpdateResult;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionFinishRequest;

public interface TemplateAuctionsRepository {
    UpdateResult finish(String auctionId, AuctionFinishRequest request);
}
