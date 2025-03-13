package com.wosarch.buysell.buysell.model.auctions.services;

import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.attachments.AttachmentWithContent;

import java.util.List;

public interface AuctionAttachmentsService {

    List<AttachmentWithContent> getAuctionAttachmentsWithContent(Auction auction);

    void removeAuctionAttachments(Auction auction);
}
