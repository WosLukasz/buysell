package com.wosarch.buysell.buysell.model.auctions.requests;

import com.wosarch.buysell.buysell.model.auctions.SellerProfile;
import com.wosarch.buysell.buysell.model.common.Amount;
import com.wosarch.buysell.buysell.model.attachments.Attachment;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AuctionCreationRequest {
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String categoryId;
    @NotNull
    private Amount price;
    @NotNull
    private List<Attachment> attachments;
    @NotNull
    private SellerProfile seller;
}
