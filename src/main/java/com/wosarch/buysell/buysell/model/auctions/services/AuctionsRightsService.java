package com.wosarch.buysell.buysell.model.auctions.services;

import com.wosarch.buysell.buysell.model.auctions.Auction;

public interface AuctionsRightsService {

    void validateAuctionAccessibility(Auction auction);
}
