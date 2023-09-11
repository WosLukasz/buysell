package com.wosarch.buysell.model.auctions;

import com.wosarch.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.model.auctions.requests.AuctionFinishRequest;

public interface AuctionsService {

    Auction create(AuctionCreationRequest request);

    Auction get(String auctionId);

    Auction save(Auction auction);

    Auction finish(String auctionId, AuctionFinishRequest request);

}
