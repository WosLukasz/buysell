package com.wosarch.buysell.buysell.repositories.views;

public interface TemplateAuctionsViewsRepository {

    Integer getViews(String auctionSignature);
    Integer incrementViews(String auctionSignature, String remoteAddress);
}
