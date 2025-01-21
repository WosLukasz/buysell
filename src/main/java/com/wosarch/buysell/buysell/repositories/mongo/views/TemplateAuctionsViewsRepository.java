package com.wosarch.buysell.buysell.repositories.mongo.views;

public interface TemplateAuctionsViewsRepository {

    Integer getViews(String auctionSignature);
    Integer incrementViews(String auctionSignature, String remoteAddress);
}
