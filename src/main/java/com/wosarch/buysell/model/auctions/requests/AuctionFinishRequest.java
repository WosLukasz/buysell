package com.wosarch.buysell.model.auctions.requests;

import com.wosarch.buysell.model.auctions.AuctionFinishReason;
import lombok.Data;

@Data
public class AuctionFinishRequest {
    private AuctionFinishReason reason;
}
