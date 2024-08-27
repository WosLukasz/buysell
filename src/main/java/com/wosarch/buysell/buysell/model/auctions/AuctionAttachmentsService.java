package com.wosarch.buysell.buysell.model.auctions;

import com.wosarch.buysell.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.common.model.attachments.Attachment;
import com.wosarch.buysell.common.model.attachments.AttachmentWithContent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AuctionAttachmentsService {

    List<Attachment> saveAuctionAttachments(AuctionCreationRequest request, String auctionSignature);

    List<AttachmentWithContent> getAuctionAttachmentsWithContent(Auction auction);

    void removeAuctionAttachments(Auction auction);

    List<Attachment> changeAuctionAttachments(List<Attachment> currentAttachments, List<MultipartFile> newFiles, String auctionSignature);


}
