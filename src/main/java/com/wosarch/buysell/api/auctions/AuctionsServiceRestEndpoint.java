package com.wosarch.buysell.api.auctions;

import com.wosarch.buysell.model.auctions.Auction;
import com.wosarch.buysell.model.auctions.AuctionsService;
import com.wosarch.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.model.auctions.requests.AuctionFinishRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auctions")
public class AuctionsServiceRestEndpoint {

    Logger logger = LoggerFactory.getLogger(AuctionsServiceRestEndpoint.class);

    @Autowired
    private AuctionsService auctionsService;

    @RequestMapping(method = RequestMethod.GET, path = "/{signature}")
    public ResponseEntity<Auction> get(@PathVariable String signature) {
        logger.debug("Getting auction with signature {}", signature);

        return new ResponseEntity<>(auctionsService.get(signature), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Auction> create(@RequestBody @Valid AuctionCreationRequest request) {
        return new ResponseEntity<>(auctionsService.create(request), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{id}")
    public ResponseEntity<Auction> save(@RequestBody Auction auction) {
        return new ResponseEntity<>(auctionsService.save(auction), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PATCH, path = "/{id}/finish")
    public ResponseEntity<Auction> finish(@PathVariable String id, @RequestBody AuctionFinishRequest request) {
        return new ResponseEntity<>(auctionsService.finish(id, request), HttpStatus.OK);
    }

}
