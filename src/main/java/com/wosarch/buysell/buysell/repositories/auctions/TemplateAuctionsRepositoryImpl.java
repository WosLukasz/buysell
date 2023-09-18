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
}
