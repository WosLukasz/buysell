package com.wosarch.buysell.auctions.buysell.model.auctions;

import com.wosarch.buysell.auctions.buysell.model.auctions.search.AuctionsSearchRequest;
import com.wosarch.buysell.auctions.buysell.model.auctions.search.AuctionsSearchResponse;

public interface AuctionsSearchService {

    AuctionsSearchResponse search(AuctionsSearchRequest request);
}
