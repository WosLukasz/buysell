package com.wosarch.buysell.services.auctions;

import com.mongodb.client.result.UpdateResult;
import com.wosarch.buysell.model.auctions.Auction;
import com.wosarch.buysell.model.auctions.AuctionStatus;
import com.wosarch.buysell.model.auctions.AuctionsService;
import com.wosarch.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.model.auctions.requests.AuctionFinishRequest;
import com.wosarch.buysell.model.common.sequence.SequenceService;
import com.wosarch.buysell.repositories.auctions.AuctionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionsServiceImpl implements AuctionsService {

    @Autowired
    private AuctionsRepository auctionsRepository;

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
        auction.setViews(0l);
        auction.setContactInformation(request.getContactInformation());
        auction.setOwnerId(request.getOwnerId());
        auction.setAttachments(request.getAttachments());

        return auctionsRepository.save(auction);
    }

    @Override
    public Auction get(String auctionId) {
        return auctionsRepository.findBySignature(auctionId).get();
    }

    @Override
    public Auction save(Auction auction) {
        return auctionsRepository.save(auction);
    }

    @Override
    public Auction finish(String auctionId, AuctionFinishRequest request) {
        UpdateResult updateResult = auctionsRepository.finish(auctionId, request);

        return auctionsRepository.findById(auctionId).get();
    }
}
