package com.wosarch.buysell.buysell.services.auctions;

import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.AuctionAttachmentsService;
import com.wosarch.buysell.common.model.attachments.AttachmentWithContent;
import com.wosarch.buysell.common.model.attachments.AttachmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionAttachmentsServiceImpl implements AuctionAttachmentsService {

    private static final String AUCTIONS_PREFIX_PATH = "auctions";

    @Autowired
    private AttachmentsService attachmentsService;


    @Override
    public List<AttachmentWithContent> getAuctionAttachmentsWithContent(Auction auction) {
        return attachmentsService.getAttachmentsWithContent(auction.getAttachments());
    }

    @Override
    public void removeAuctionAttachments(Auction auction) {
        attachmentsService.removeAttachments(auction.getAttachments());
        auction.setAttachments(null);
    }
}
