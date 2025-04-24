package com.wosarch.buysell.auctions.buysell.model.auctions.search;

import com.wosarch.buysell.auctions.buysell.model.auctions.Auction;
import lombok.Data;

import java.util.List;

@Data
public class AuctionsSearchResponse {

    private List<Auction> auctions;
    private long total;
}
