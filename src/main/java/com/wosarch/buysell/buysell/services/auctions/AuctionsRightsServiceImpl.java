package com.wosarch.buysell.buysell.services.auctions;

import com.wosarch.buysell.admin.model.auth.RequestContextService;
import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.services.AuctionsRightsService;
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
