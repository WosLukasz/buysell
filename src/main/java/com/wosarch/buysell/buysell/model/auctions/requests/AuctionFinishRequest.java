package com.wosarch.buysell.buysell.model.auctions.requests;

import com.wosarch.buysell.buysell.model.auctions.enums.AuctionFinishReason;
import lombok.Data;

@Data
public class AuctionFinishRequest {
    private AuctionFinishReason reason;
}
