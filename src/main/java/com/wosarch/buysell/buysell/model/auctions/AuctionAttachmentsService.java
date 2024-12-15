package com.wosarch.buysell.buysell.model.auctions;

import com.wosarch.buysell.common.model.attachments.AttachmentWithContent;

import java.util.List;

public interface AuctionAttachmentsService {

    List<AttachmentWithContent> getAuctionAttachmentsWithContent(Auction auction);

    void removeAuctionAttachments(Auction auction);
}
