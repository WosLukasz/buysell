package com.wosarch.buysell.buysell.model.auctions.requests;

import com.wosarch.buysell.buysell.model.auctions.Auction;
import lombok.Data;

import java.util.List;

@Data
public class AuctionsSearchResponse {

    private List<Auction> auctions;
    private long total;
}
