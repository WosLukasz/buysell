package com.wosarch.buysell.buysell.services.auctions;

import com.mongodb.client.result.UpdateResult;
import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.AuctionStatus;
import com.wosarch.buysell.buysell.model.auctions.AuctionsService;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionFinishRequest;
import com.wosarch.buysell.buysell.repositories.views.AuctionsViewsRepository;
import com.wosarch.buysell.common.model.exception.BuysellException;
import com.wosarch.buysell.common.model.sequence.SequenceService;
import com.wosarch.buysell.buysell.repositories.auctions.AuctionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuctionsServiceImpl implements AuctionsService {

    @Autowired
    private AuctionsRepository auctionsRepository;

    @Autowired
    private AuctionsViewsRepository auctionsViewsRepository;

    @Autowired
    private SequenceService sequenceService;

    @Override
    public Auction create(AuctionCreationRequest request) {
        Auction auction = new Auction();
        auction.setSignature(sequenceService.getNext(Auction.SEQUENCE_NAME));
        auction.setTitle(request.getTitle());
        auction.setCategory(request.getCategoryId());
        auction.setStatus(AuctionStatus.QUEUED);
        auction.setDescription(request.getDescription());
        auction.setLocation(request.getLocation());
        auction.setContactInformation(request.getContactInformation());
        auction.setOwnerId(request.getOwnerId());
        auction.setAttachments(request.getAttachments());

        return auctionsRepository.save(auction);
    }

    @Override
    public Auction get(String signature) throws BuysellException {
        Optional<Auction> optionalAuction = auctionsRepository.findBySignature(signature);
        if (!optionalAuction.isPresent()) {
            throw new BuysellException("Auction not found");
        }

        return optionalAuction.get();
    }

    @Override
    public Auction save(Auction auction) {
        return auctionsRepository.save(auction);
    }

    @Override
    public Auction finish(String signature, AuctionFinishRequest request) throws BuysellException {
        Auction auction = get(signature);
        auction.setStatus(AuctionStatus.CLOSED);
        auction.setFinishReason(request.getReason());

        return auctionsRepository.save(auction);
    }

    @Override
    public Integer getViews(String signature) {
        return auctionsViewsRepository.getViews(signature);
    }

    @Override
    public Integer incrementViews(String signature, String remoteAddress) {
        return auctionsViewsRepository.incrementViews(signature, remoteAddress);
    }
}
