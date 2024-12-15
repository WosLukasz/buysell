package com.wosarch.buysell.buysell.model.auctions;

import com.wosarch.buysell.buysell.model.auctions.requests.AuctionsSearchRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionsSearchResponse;

public interface AuctionsSearchService {

    AuctionsSearchResponse search(AuctionsSearchRequest request);
}
