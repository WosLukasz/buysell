package com.wosarch.buysell.buysell.model.auctions;

public interface AuctionsRightsService {

    void validateAuctionAccessibility(String signature);

    void validateAuctionAccessibility(Auction auction);
}
