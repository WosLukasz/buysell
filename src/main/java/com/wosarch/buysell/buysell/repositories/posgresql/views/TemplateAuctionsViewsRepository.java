package com.wosarch.buysell.buysell.repositories.posgresql.views;

public interface TemplateAuctionsViewsRepository {

    Long getViews(String auctionSignature);

    Long incrementViews(String auctionSignature, String remoteAddress);
}
