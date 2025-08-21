package com.wosarch.buysell.auctions.buysell.api.auctions;

import com.wosarch.buysell.auctions.buysell.model.auctions.AuctionsSearchService;
import com.wosarch.buysell.auctions.buysell.model.auctions.search.AuctionsSearchRequest;
import com.wosarch.buysell.auctions.buysell.model.auctions.search.AuctionsSearchResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/auctions-search")
public class AuctionsSearchServiceRestEndpoint {

    Logger logger = LoggerFactory.getLogger(AuctionsSearchServiceRestEndpoint.class);

    @Autowired
    private AuctionsSearchService auctionsSearchService;

    @PreAuthorize("permitAll()")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AuctionsSearchResponse> search(@RequestBody @Valid AuctionsSearchRequest request) {
        return new ResponseEntity<>(auctionsSearchService.search(request), HttpStatus.OK);
    }

}