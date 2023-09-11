package com.wosarch.buysell.api.auctions;

import com.wosarch.buysell.model.auctions.Auction;
import com.wosarch.buysell.model.auctions.AuctionsService;
import com.wosarch.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.model.auctions.requests.AuctionFinishRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auctions")
public class AuctionsServiceRestEndpoint {

    @Autowired
    private AuctionsService auctionsService;

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<Auction> get(@PathVariable String id) {
        return new ResponseEntity<>(auctionsService.get(id), HttpStatus.OK);
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
