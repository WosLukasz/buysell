package com.wosarch.buysell.buysell.services.auctions;

import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.AuctionsSearchService;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionsSearchRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionsSearchResponse;
import com.wosarch.buysell.buysell.repositories.auctions.AuctionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionsSearchServiceImpl implements AuctionsSearchService {

    @Autowired
    private AuctionsRepository auctionsRepository;

    @Override
    public AuctionsSearchResponse search(AuctionsSearchRequest request) {
        List<Auction> auctions = auctionsRepository.findAll(); // for tests
        AuctionsSearchResponse response = new AuctionsSearchResponse();
        response.setAuctions(auctions);
        response.setTotal(auctions.size());

        return response;
    }
}
