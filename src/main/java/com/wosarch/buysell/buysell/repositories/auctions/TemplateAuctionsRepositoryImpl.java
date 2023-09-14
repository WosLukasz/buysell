package com.wosarch.buysell.buysell.repositories.auctions;

import com.mongodb.client.result.UpdateResult;
import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionFinishRequest;
import com.wosarch.buysell.buysell.model.auctions.AuctionStatus;
import com.wosarch.buysell.buysell.repositories.BuysellRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateAuctionsRepositoryImpl extends BuysellRepository implements TemplateAuctionsRepository {

    Logger logger = LoggerFactory.getLogger(TemplateAuctionsRepositoryImpl.class);

    @Override
    public UpdateResult finish(String auctionId, AuctionFinishRequest request) {
        Query query = new Query(Criteria.where(Auction.Fields.ID).is(auctionId));
        Update update = new Update();
        update.set(Auction.Fields.STATUS, AuctionStatus.CLOSED);
        update.set(Auction.Fields.FINISH_REASON, request.getReason());

        UpdateResult result = mongoTemplate.updateFirst(query, update, Auction.class);

        if (result == null) {
            logger.debug("No documents updated");
        } else {
            logger.debug("{} document(s) updated..", result.getModifiedCount());
        }

        return result;

    }
}
