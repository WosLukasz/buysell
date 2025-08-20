package com.wosarch.buysell.auctions.buysell.model.auctions;

import com.wosarch.buysell.auctions.buysell.model.auctions.requests.AuctionFinishRequest;
import com.wosarch.buysell.auctions.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.auctions.buysell.model.auctions.requests.AuctionReportRequest;
import com.wosarch.buysell.auctions.common.model.exception.BuysellException;

import java.util.Date;

public interface AuctionsService {

    Auction create(AuctionCreationRequest request);

    Auction get(String signature) throws BuysellException;

    Auction nullableGet(String signature);

    Auction save(Auction auction);

    Auction finish(String signature, AuctionFinishRequest request) throws BuysellException;

    Auction refresh(String signature);

    Auction removeAuctionAttachments(String signature) throws BuysellException;

    Auction report(String signature, AuctionReportRequest request) throws BuysellException;

    Integer getViews(String signature);

    Integer incrementViews(String signature, String remoteAddress);

    boolean auctionActive(String auctionSignature);

    Date getExpiryDate(Date startDate);
}
