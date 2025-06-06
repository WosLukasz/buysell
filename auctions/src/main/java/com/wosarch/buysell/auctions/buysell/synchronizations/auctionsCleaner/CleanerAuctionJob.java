package com.wosarch.buysell.auctions.buysell.synchronizations.auctionsCleaner;

import com.wosarch.buysell.auctions.buysell.model.auctions.Auction;
import com.wosarch.buysell.auctions.buysell.model.auctions.AuctionAttachmentsService;
import com.wosarch.buysell.auctions.buysell.model.auctions.HistoricalAuction;
import com.wosarch.buysell.auctions.buysell.repositories.elastic.auctions.AuctionsElasticSearchRepository;
import com.wosarch.buysell.auctions.buysell.repositories.mongo.auctions.AuctionsRepository;
import com.wosarch.buysell.auctions.buysell.repositories.mongo.historicalauctions.HistoricalAuctionsRepository;
import lombok.Builder;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.Callable;

@Builder
public class CleanerAuctionJob implements Callable<Void> {

    private Auction auction;

    private AuctionsRepository auctionsRepository;

    private Logger logger;

    private HistoricalAuctionsRepository historicalAuctionsRepository;

    private AuctionAttachmentsService auctionAttachmentsService;

    private AuctionsElasticSearchRepository auctionsElasticSearchRepository;

    @Override
    public Void call() throws Exception {
        moveAuctionToHistoryCollection(auction);
        return null;
    }

    @Transactional
    private void moveAuctionToHistoryCollection(Auction auction) {
        HistoricalAuction historicalAuction = createHistoricalAuction(auction);
        historicalAuctionsRepository.save(historicalAuction);
        auctionsElasticSearchRepository.delete(auction);
        auctionsRepository.delete(auction);
        auctionAttachmentsService.removeAuctionAttachments(auction);

        logger.info("Auction {} cleaned", auction.getSignature());
    }

    private HistoricalAuction createHistoricalAuction(Auction auction) {
        HistoricalAuction historicalAuction = new HistoricalAuction();
        historicalAuction.setAuction(auction);
        historicalAuction.setMoveDate(new Date());

        return historicalAuction;
    }

}
