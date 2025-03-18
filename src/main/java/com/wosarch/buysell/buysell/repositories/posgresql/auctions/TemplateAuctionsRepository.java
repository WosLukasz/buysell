package com.wosarch.buysell.buysell.repositories.posgresql.auctions;

import com.wosarch.buysell.buysell.model.auctions.Auction;

import java.util.Optional;

public interface TemplateAuctionsRepository {

    Optional<Auction> findBySignature(String signature);

}
