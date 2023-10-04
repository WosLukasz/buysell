package com.wosarch.buysell.buysell.model.auctions;

import com.wosarch.buysell.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.common.model.attachments.Attachment;

import java.util.List;

public interface AuctionAttachmentsService {

    List<Attachment> saveAuctionAttachments(AuctionCreationRequest request, Auction auction);
}
