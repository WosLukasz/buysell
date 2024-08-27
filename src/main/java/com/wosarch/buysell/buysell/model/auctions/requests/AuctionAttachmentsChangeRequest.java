package com.wosarch.buysell.buysell.model.auctions.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class AuctionAttachmentsChangeRequest {
    @NotNull
    private String signature;
    @NotNull
    private List<MultipartFile> newFiles;

}
