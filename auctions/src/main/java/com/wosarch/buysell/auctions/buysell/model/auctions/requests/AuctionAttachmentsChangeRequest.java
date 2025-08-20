package com.wosarch.buysell.auctions.buysell.model.auctions.requests;

import com.wosarch.buysell.auctions.common.model.attachment.AttachmentSaveRequest;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AuctionAttachmentsChangeRequest {
    @NotNull
    private String signature;
    @NotNull
    private List<AttachmentSaveRequest> newFiles;

}
