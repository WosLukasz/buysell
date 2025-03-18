package com.wosarch.buysell.buysell.services.auctions;

import com.wosarch.buysell.admin.model.auth.RequestContextService;
import com.wosarch.buysell.buysell.model.auctions.*;
import com.wosarch.buysell.buysell.model.auctions.enums.AuctionStatus;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionFinishRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionReportRequest;
import com.wosarch.buysell.buysell.model.auctions.services.AuctionAttachmentsService;
import com.wosarch.buysell.buysell.model.auctions.services.AuctionsRightsService;
import com.wosarch.buysell.buysell.model.auctions.services.AuctionsService;
import com.wosarch.buysell.buysell.repositories.elastic.auctions.AuctionsElasticSearchRepository;
import com.wosarch.buysell.buysell.repositories.posgresql.auctions.AuctionsRepository;
import com.wosarch.buysell.buysell.repositories.posgresql.auctionssignaturesequence.AuctionsSignatureSequenceRepository;
import com.wosarch.buysell.buysell.repositories.posgresql.sellers.SellersRepository;
import com.wosarch.buysell.buysell.repositories.posgresql.views.AuctionsViewsRepository;
import com.wosarch.buysell.buysell.model.attachments.AttachmentWithContent;
import com.wosarch.buysell.common.model.exception.BuysellException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class AuctionsServiceImpl implements AuctionsService {

    @Value("${buySell.config.auctionDurationDays}")
    private String auctionDurationDays;

    @Autowired
    private AuctionsRepository auctionsRepository;

    @Autowired
    private AuctionsElasticSearchRepository auctionsElasticSearchRepository;

    @Autowired
    private AuctionsViewsRepository auctionsViewsRepository;

    @Autowired
    private SellersRepository sellersRepository;

    @Autowired
    private AuctionsSignatureSequenceRepository auctionsSignatureSequenceRepository;

    @Autowired
    private AuctionAttachmentsService auctionAttachmentsService;

    @Autowired
    private AuctionsRightsService auctionsRightsService;

    @Autowired
    private RequestContextService requestContextService;

    @Override
    @Transactional(value = "buySellTransactionManager")
    public Auction create(AuctionCreationRequest request) {
        String ownerId = requestContextService.getCurrentUserId();
        SellerProfile seller = request.getSeller();
        seller.setOwnerId(ownerId);
        SellerProfile savedSeller = sellersRepository.save(seller);
        Auction auction = new Auction();
        auction.setSignature(nextSignature());
        auction.setTitle(request.getTitle());
        auction.setPrice(request.getPrice());
        auction.setCategory(request.getCategoryId());
        auction.setStatus(AuctionStatus.QUEUED);
        auction.setDescription(request.getDescription());
        auction.setSellerId(savedSeller.getId());
        auction.setOwnerId(ownerId);
        auction.setAttachments(request.getAttachments());

        return save(auction);
    }

    private String nextSignature() {
        return auctionsSignatureSequenceRepository.save(new AuctionSignatureSequence()).getSignature().toString();
    }

    @Override
    public Auction get(String signature) throws BuysellException {
        Optional<Auction> optionalAuction = auctionsRepository.findBySignature(signature);
        if (optionalAuction.isEmpty()) {
            throw new BuysellException("Auction not found");
        }

        return optionalAuction.get();
    }

    @Override
    public SellerProfile getSellerProfileByAuctionSignature(String signature) throws BuysellException {
        Auction auction = get(signature);
        Optional<SellerProfile> optionalSeller = sellersRepository.findById(auction.getSellerId());
        if (optionalSeller.isEmpty()) {
            throw new BuysellException("Auction not found");
        }

        return optionalSeller.get();
    }

    @Override
    public Auction nullableGet(String signature) {
        Optional<Auction> optionalAuction = auctionsRepository.findBySignature(signature);
        if (optionalAuction.isEmpty()) {
            return null;
        }

        return optionalAuction.get();
    }

    @Override
    @Transactional
    public Auction save(Auction auction) {
        Auction savedAuction = auctionsRepository.save(auction);
        auctionsElasticSearchRepository.save(savedAuction);

        return savedAuction;
    }

    @Override
    public Auction finish(String signature, AuctionFinishRequest request) throws BuysellException {
        Auction auction = get(signature);
        auctionsRightsService.validateAuctionAccessibility(auction);
        auction.setStatus(AuctionStatus.CLOSED);
        auction.setFinishReason(request.getReason());

        return save(auction);
    }

    @Override
    public Auction refresh(String signature) {
        Auction auction = get(signature);
        auctionsRightsService.validateAuctionAccessibility(auction);
        auction.setStatus(AuctionStatus.ACTIVE);
        auction.setLastRefreshmentDate(new Date());
        auction.setExpiryDate(getExpiryDate(auction.getLastRefreshmentDate()));
        auction.setEndDate(null);

        return save(auction);
    }

    @Override
    public List<AttachmentWithContent> getAuctionAttachmentsWithContent(String signature) throws BuysellException {
        Auction auction = get(signature);

        return auctionAttachmentsService.getAuctionAttachmentsWithContent(auction);
    }

    @Override
    public Auction removeAuctionAttachments(String signature) throws BuysellException {
        Auction auction = get(signature);
        auctionsRightsService.validateAuctionAccessibility(auction);
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

        return save(auction);
    }

    @Override
    public Long getViews(String signature) {
        return auctionsViewsRepository.getViews(signature);
    }

    @Override
    public Long incrementViews(String signature, String remoteAddress) {
        return auctionsViewsRepository.incrementViews(signature, remoteAddress);
    }

    @Override
    public boolean auctionActive(String auctionSignature) {
        Auction auction = nullableGet(auctionSignature);

        return Objects.nonNull(auction) && AuctionStatus.ACTIVE.equals(auction.getStatus());
    }

    @Override
    public Date getExpiryDate(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, Integer.parseInt(auctionDurationDays));

        return calendar.getTime();
    }

    private AuctionReport prepareReport(AuctionReportRequest request) {
        AuctionReport report = new AuctionReport();
        report.setMessage(request.getMessage());
        report.setReason(request.getReason());
        report.setUserId(requestContextService.getCurrentUserId());

        return report;
    }
}
