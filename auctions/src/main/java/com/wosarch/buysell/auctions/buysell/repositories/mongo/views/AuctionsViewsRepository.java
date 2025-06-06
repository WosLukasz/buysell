package com.wosarch.buysell.auctions.buysell.repositories.mongo.views;

import com.wosarch.buysell.auctions.buysell.model.auctions.Auction;
import com.wosarch.buysell.auctions.buysell.model.auctions.AuctionView;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionsViewsRepository extends MongoRepository<AuctionView, String>, TemplateAuctionsViewsRepository {

    Optional<Auction> findByAuctionSignature(String auctionSignature);
}
