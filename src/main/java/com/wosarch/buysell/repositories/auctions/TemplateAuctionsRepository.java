package com.wosarch.buysell.repositories.auctions;

import com.mongodb.client.result.UpdateResult;
import com.wosarch.buysell.model.auctions.Auction;
import com.wosarch.buysell.model.auctions.requests.AuctionFinishRequest;

public interface TemplateAuctionsRepository {
    UpdateResult finish(String auctionId, AuctionFinishRequest request);
}
