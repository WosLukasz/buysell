package com.wosarch.buysell.buysell.services.auctions;

import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.AuctionAttachmentsService;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.common.model.attachments.Attachment;
import com.wosarch.buysell.common.model.attachments.AttachmentWithContent;
import com.wosarch.buysell.common.model.attachments.AttachmentsService;
import com.wosarch.buysell.common.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

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

    public List<Attachment> saveAuctionAttachments(AuctionCreationRequest request, String auctionSignature) {
        List<Attachment> attachments = new ArrayList<>();
        if (CollectionUtils.isEmpty(request.getAttachments())) {
            return attachments;
        }

        request.getAttachments().forEach(file -> {
            String path = preparePathForAuctionFile(auctionSignature, prepareAuctionFileName());
            attachments.add(attachmentsService.saveAttachment(path, file));
        });

        return attachments;
    }

    @Override
    public List<AttachmentWithContent> getAuctionAttachmentsWithContent(Auction auction) {
        return attachmentsService.getAttachmentsWithContent(auction.getAttachments());
    }

    @Override
    public void removeAuctionAttachments(Auction auction) {
        attachmentsService.removeAttachments(auction.getAttachments());
        auction.setAttachments(null);
    }

    @Override
    public List<Attachment> changeAuctionAttachments(List<Attachment> currentAttachments, List<MultipartFile> newFiles, String auctionSignature) {
        attachmentsService.removeAttachments(currentAttachments);
        List<Attachment> newAttachments = new ArrayList<>();
        if (CollectionUtils.isEmpty(newFiles)) {
            return newAttachments;
        }

        newFiles.forEach(file -> {
            String path = preparePathForAuctionFile(auctionSignature, prepareAuctionFileName());
            newAttachments.add(attachmentsService.saveAttachment(path, file));
        });

        return newAttachments;
    }

    private String prepareAuctionFileName() {
        String datePart = CommonUtils.getDateAsString(new Date(), AUCTIONS_FILENAME_DATE_PATTERN);
        String randomPart = CommonUtils.generateRandomString(AUCTIONS_FILENAME_RANDOM_PART_LENGTH);

        return String.format("%s_%s", randomPart, datePart);
    }

    private String preparePathForAuctionFile(String auctionSignature, String fileName) {
        return String.format("%s/%s/%s", AUCTIONS_PREFIX_PATH, auctionSignature, fileName);
    }

    private String preparePathForAuctionDirectory(String auctionSignature) {
        return String.format("%s/%s", AUCTIONS_PREFIX_PATH, auctionSignature);
    }
}
