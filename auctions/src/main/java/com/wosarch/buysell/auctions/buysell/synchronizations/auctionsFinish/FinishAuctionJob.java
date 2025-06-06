package com.wosarch.buysell.auctions.buysell.synchronizations.auctionsFinish;

import com.wosarch.buysell.auctions.buysell.model.auctions.Auction;
import com.wosarch.buysell.auctions.buysell.model.auctions.AuctionFinishReason;
import com.wosarch.buysell.auctions.buysell.model.auctions.AuctionStatus;
import com.wosarch.buysell.auctions.buysell.model.auctions.AuctionsService;
import lombok.Builder;
import org.slf4j.Logger;

import java.util.Date;
import java.util.concurrent.Callable;

@Builder
public class FinishAuctionJob implements Callable<Void> {

    private Auction auction;

    private AuctionsService auctionsService;

    private Logger logger;

    @Override
    public Void call() throws Exception {
        finishAuction(auction);
        return null;
    }

    private void finishAuction(Auction auction) {
        auction.setStatus(AuctionStatus.CLOSED);
        auction.setFinishReason(AuctionFinishReason.EXPIRED);
        auction.setEndDate(new Date());
        auctionsService.save(auction);
        logger.info("Auction {} finished", auction.getSignature());
    }

}
