package com.wosarch.buysell.repositories.auctions;

import com.mongodb.client.result.UpdateResult;
import com.wosarch.buysell.model.auctions.Auction;
import com.wosarch.buysell.model.auctions.AuctionStatus;
import com.wosarch.buysell.model.auctions.requests.AuctionFinishRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateAuctionsRepositoryImpl implements TemplateAuctionsRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UpdateResult finish(String auctionId, AuctionFinishRequest request) {
        Query query = new Query(Criteria.where(Auction.Fields.ID).is(auctionId));
        Update update = new Update();
        update.set(Auction.Fields.STATUS, AuctionStatus.CLOSED);
        update.set(Auction.Fields.FINISH_REASON, request.getReason());

        UpdateResult result = mongoTemplate.updateFirst(query, update, Auction.class);

        if(result == null)
            System.out.println("No documents updated");
        else
            System.out.println(result.getModifiedCount() + " document(s) updated..");

        return result;

    }
}
