package com.wosarch.buysell.auctions.buysell.repositories.mongo.views;

public interface TemplateAuctionsViewsRepository {

    Integer getViews(String auctionSignature);
    Integer incrementViews(String auctionSignature, String remoteAddress);
}
