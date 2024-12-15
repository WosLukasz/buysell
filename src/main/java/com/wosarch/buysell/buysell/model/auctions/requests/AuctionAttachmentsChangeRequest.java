package com.wosarch.buysell.buysell.model.auctions.requests;

import com.wosarch.buysell.common.model.attachments.AttachmentSaveRequest;
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
