package com.wosarch.buysell.buysell.model.auctions.requests;

import com.wosarch.buysell.buysell.model.auctions.enums.AuctionReportReason;
import lombok.Data;

@Data
public class AuctionReportRequest {
    private AuctionReportReason reason;
    private String message;
}
