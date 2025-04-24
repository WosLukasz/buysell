package com.wosarch.buysell.auctions.buysell.model.auctions;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuctionReport implements Serializable  {
    private AuctionReportReason reason;
    private String message;
    private String userId;
}
