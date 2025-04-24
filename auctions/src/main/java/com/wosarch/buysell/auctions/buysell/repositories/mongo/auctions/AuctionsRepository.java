package com.wosarch.buysell.auctions.buysell.repositories.mongo.auctions;

import com.wosarch.buysell.auctions.buysell.model.auctions.Auction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionsRepository extends MongoRepository<Auction, String>, TemplateAuctionsRepository {

    Optional<Auction> findBySignature(String signature);

    default Auction save(Auction auction) {
        return daoSave(auction);
    }
}
