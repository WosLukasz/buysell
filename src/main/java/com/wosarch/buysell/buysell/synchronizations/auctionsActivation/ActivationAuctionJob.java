package com.wosarch.buysell.buysell.synchronizations.auctionsActivation;

import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.enums.AuctionStatus;
import com.wosarch.buysell.buysell.model.auctions.services.AuctionsService;
import lombok.Builder;
import org.slf4j.Logger;

import java.util.Date;
import java.util.concurrent.Callable;

@Builder
public class ActivationAuctionJob implements Callable<Void> {

    private Auction auction;

    private AuctionsService auctionsService;

    private Logger logger;

    @Override
    public Void call() throws Exception {
        activateAuction(auction);
        return null;
    }

    private void activateAuction(Auction auction) {
        if (!validateAuction(auction)) {
            logger.warn("Auction can not be activated automatically. Please check it manually.");
            auction.setToCheckManually(Boolean.TRUE);
            auctionsService.save(auction);

            return;
        }

        auction.setStatus(AuctionStatus.ACTIVE);
        auction.setStartDate(new Date());
        auction.setExpiryDate(auctionsService.getExpiryDate(auction.getStartDate()));
        auctionsService.save(auction);
        logger.info("Auction {} activated", auction.getSignature());
    }

    private boolean validateAuction(Auction auction) {
        // implement auction validation business logic to trigger
        return true;
    }


}
