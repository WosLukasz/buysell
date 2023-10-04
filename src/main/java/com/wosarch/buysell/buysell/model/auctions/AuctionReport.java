package com.wosarch.buysell.buysell.model.auctions;

import lombok.Data;

@Data
public class AuctionReport {
    private AuctionReportReason reason;
    private String message;
    private String userId;
}
