package com.wosarch.buysell.buysell.model.auctions.requests;

import com.wosarch.buysell.buysell.model.common.ContactInformation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    private List<MultipartFile> attachments;
    @NotNull
    private String location;
    @NotNull
    private List<ContactInformation> contactInformation;
    @NotNull
    private String ownerId;

}
