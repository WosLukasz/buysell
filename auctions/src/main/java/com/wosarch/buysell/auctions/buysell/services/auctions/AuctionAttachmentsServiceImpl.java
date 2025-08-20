package com.wosarch.buysell.auctions.buysell.services.auctions;

import com.wosarch.buysell.auctions.buysell.model.auctions.Auction;
import com.wosarch.buysell.auctions.buysell.model.auctions.AuctionAttachmentsService;
import com.wosarch.buysell.auctions.restclient.attachments.AttachmentClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionAttachmentsServiceImpl implements AuctionAttachmentsService {

    private static final String AUCTIONS_PREFIX_PATH = "auctions";

    @Autowired
    private AttachmentClient attachmentClient;

    @Override
    public Boolean removeAuctionAttachments(Auction auction) {
        Boolean response = attachmentClient.removeAttachments(auction.getAttachments());
        if (Boolean.FALSE.equals(response)) {
            // excpetion
        }

        auction.setAttachments(null);

        return response;

    }
}
