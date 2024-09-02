package com.wosarch.buysell.buysell.services.auctions;

import com.wosarch.buysell.buysell.model.auctions.*;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionAttachmentsChangeRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionFinishRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionReportRequest;
import com.wosarch.buysell.buysell.repositories.auctions.AuctionsRepository;
import com.wosarch.buysell.buysell.repositories.views.AuctionsViewsRepository;
import com.wosarch.buysell.common.model.attachments.Attachment;
import com.wosarch.buysell.common.model.attachments.AttachmentWithContent;
import com.wosarch.buysell.common.model.exception.BuysellException;
import com.wosarch.buysell.common.model.sequence.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AuctionsServiceImpl implements AuctionsService {

    @Autowired
    private AuctionsRepository auctionsRepository;

    @Autowired
    private AuctionsViewsRepository auctionsViewsRepository;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private AuctionAttachmentsService auctionAttachmentsService;

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
        auction.setAttachments(auctionAttachmentsService.saveAuctionAttachments(request, auction.getSignature()));

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
    public List<AttachmentWithContent> getAuctionAttachmentsWithContent(String signature) throws BuysellException {
        Auction auction = get(signature);

        return auctionAttachmentsService.getAuctionAttachmentsWithContent(auction);
    }

    @Override
    public Auction removeAuctionAttachments(String signature) throws BuysellException {
        Auction auction = get(signature);
        auctionAttachmentsService.removeAuctionAttachments(auction);

        return save(auction);
    }

    @Override
    @Transactional
    public Auction changeAuctionAttachments(AuctionAttachmentsChangeRequest request) throws BuysellException {
        Auction auction = get(request.getSignature());
        List<Attachment> newAttachments = auctionAttachmentsService.changeAuctionAttachments(auction.getAttachments(), request.getNewFiles(), auction.getSignature());
        auction.setAttachments(newAttachments);
        // test
        Random random = new Random();
        final int MAX = 2;
        final int MIN = 1;
        Integer randInt = random.nextInt((MAX - MIN) + 1) + MIN;
        if (randInt.equals(1) || randInt.equals(2)) {
            throw new RuntimeException();
        }
        // test

        return save(auction);
    }

    @Override
    public Auction report(String signature, AuctionReportRequest request) throws BuysellException {
        Auction auction = get(signature);
        if (CollectionUtils.isEmpty(auction.getReports())) {
            auction.setReports(new ArrayList<>());
        }
        auction.getReports().add(prepareReport(request));

        return auctionsRepository.save(auction);
    }

    private AuctionReport prepareReport(AuctionReportRequest request) {
        AuctionReport report = new AuctionReport();
        report.setMessage(request.getMessage());
        report.setReason(request.getReason());
        report.setUserId(null); //TODO: Add user who reported Auction

        return report;
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
