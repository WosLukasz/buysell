package com.wosarch.buysell.buysell.model.auctions;

import com.wosarch.buysell.buysell.model.auctions.requests.AuctionFinishRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionReportRequest;
import com.wosarch.buysell.common.model.attachments.AttachmentWithContent;
import com.wosarch.buysell.common.model.exception.BuysellException;

import java.util.List;

public interface AuctionsService {

    Auction create(AuctionCreationRequest request);

    Auction get(String signature) throws BuysellException;

    Auction save(Auction auction);

    Auction finish(String signature, AuctionFinishRequest request) throws BuysellException;

    List<AttachmentWithContent> getAuctionAttachmentsWithContent(String signature) throws BuysellException;

    Auction removeAuctionAttachments(String signature) throws BuysellException;

    Auction report(String signature, AuctionReportRequest request) throws BuysellException;

    Integer getViews(String signature);

    Integer incrementViews(String signature, String remoteAddress);
}
