package com.wosarch.buysell.model.auctions.requests;

import com.wosarch.buysell.model.common.Attachment;
import com.wosarch.buysell.model.common.ContactInformation;
import jakarta.validation.constraints.Max;
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
    @Max(12)
    private List<Attachment> attachments;
    @NotNull
    private String location;
    @NotNull
    private List<ContactInformation> contactInformation;
    @NotNull
    private String ownerId;

}
