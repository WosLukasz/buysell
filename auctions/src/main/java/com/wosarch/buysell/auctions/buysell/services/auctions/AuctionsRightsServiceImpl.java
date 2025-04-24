package com.wosarch.buysell.auctions.buysell.services.auctions;

import com.wosarch.buysell.auctions.common.model.auth.RequestContextService;
import com.wosarch.buysell.auctions.buysell.model.auctions.Auction;
import com.wosarch.buysell.auctions.buysell.model.auctions.AuctionsRightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class AuctionsRightsServiceImpl implements AuctionsRightsService {

    @Autowired
    private RequestContextService requestContextService;

    @Override
    public void validateAuctionAccessibility(Auction auction) {
        String currentUserId = requestContextService.getCurrentUserId();
        if (!currentUserId.equals(auction.getCreatedBy())) {
            throw new AccessDeniedException("No access to this auction");
        }
    }
}
