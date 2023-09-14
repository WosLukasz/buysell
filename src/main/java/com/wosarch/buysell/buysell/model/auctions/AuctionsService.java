package com.wosarch.buysell.buysell.model.auctions;

import com.wosarch.buysell.buysell.model.auctions.requests.AuctionFinishRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionCreationRequest;

public interface AuctionsService {

    Auction create(AuctionCreationRequest request);

    Auction get(String auctionSignature);

    Auction save(Auction auction);

    Auction finish(String auctionId, AuctionFinishRequest request);

}
