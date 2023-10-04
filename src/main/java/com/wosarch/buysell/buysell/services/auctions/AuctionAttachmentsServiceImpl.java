package com.wosarch.buysell.buysell.services.auctions;

import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.AuctionAttachmentsService;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.common.model.attachments.Attachment;
import com.wosarch.buysell.common.model.attachments.AttachmentsService;
import com.wosarch.buysell.common.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuctionAttachmentsServiceImpl implements AuctionAttachmentsService {

    private static final String AUCTIONS_PREFIX_PATH = "auctions";
    private static final String AUCTIONS_FILENAME_DATE_PATTERN = "yyyy_MM_dd_HH_mm_ss";
    private static final Integer AUCTIONS_FILENAME_RANDOM_PART_LENGTH = 7;

    @Autowired
    private AttachmentsService attachmentsService;

    public List<Attachment> saveAuctionAttachments(AuctionCreationRequest request, Auction auction) {
        List<Attachment> attachments = new ArrayList<>();
        if (CollectionUtils.isEmpty(request.getAttachments())) {
            return attachments;
        }

        request.getAttachments().forEach(file -> {
            String path = preparePathForAuctionFile(auction, prepareAuctionFileName());
            attachments.add(attachmentsService.saveAttachment(path, file));
        });

        return attachments;
    }

    private String prepareAuctionFileName() {
        String datePart = CommonUtils.getDateAsString(new Date(), AUCTIONS_FILENAME_DATE_PATTERN);
        String randomPart = CommonUtils.generateRandomString(AUCTIONS_FILENAME_RANDOM_PART_LENGTH);

        return String.format("%s_%s", randomPart, datePart);
    }

    private String preparePathForAuctionFile(Auction auction, String fileName) {
        return String.format("%s/%s/%s", AUCTIONS_PREFIX_PATH, auction.getSignature(), fileName);
    }
}
