package com.wosarch.buysell.buysell.services.auctions;

import com.wosarch.buysell.admin.model.auth.RequestContextService;
import com.wosarch.buysell.buysell.model.auctions.*;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionFinishRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionReportRequest;
import com.wosarch.buysell.buysell.repositories.auctions.AuctionsRepository;
import com.wosarch.buysell.buysell.repositories.views.AuctionsViewsRepository;
import com.wosarch.buysell.common.model.attachments.AttachmentWithContent;
import com.wosarch.buysell.common.model.exception.BuysellException;
import com.wosarch.buysell.common.model.sequence.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private RequestContextService requestContextService;

    @Override
    public Auction create(AuctionCreationRequest request) {
        Auction auction = new Auction();
        auction.setSignature(sequenceService.getNext(Auction.SEQUENCE_NAME));
        auction.setTitle(request.getTitle());
        auction.setPrice(request.getPrice());
        auction.setCategory(request.getCategoryId());
        auction.setStatus(AuctionStatus.QUEUED);
        auction.setDescription(request.getDescription());
        auction.setSeller(request.getSeller());
        auction.setOwnerId(requestContextService.getCurrentUserId());
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
        report.setUserId(requestContextService.getCurrentUserId());

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
