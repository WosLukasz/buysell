package com.wosarch.buysell.buysell.api.auctions;

import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.AuctionsService;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionFinishRequest;
import com.wosarch.buysell.common.model.exception.BuysellException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auctions")
public class AuctionsServiceRestEndpoint {

    Logger logger = LoggerFactory.getLogger(AuctionsServiceRestEndpoint.class);

    @Autowired
    private AuctionsService auctionsService;

    @RequestMapping(method = RequestMethod.GET, path = "/{signature}")
    public ResponseEntity<Auction> get(@PathVariable String signature) throws BuysellException {
        logger.debug("Getting auction with signature {}", signature);

        return new ResponseEntity<>(auctionsService.get(signature), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Auction> create(@ModelAttribute @Valid AuctionCreationRequest request) {
        return new ResponseEntity<>(auctionsService.create(request), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Auction> save(@RequestBody Auction auction) {
        return new ResponseEntity<>(auctionsService.save(auction), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{signature}/finish")
    public ResponseEntity<Auction> finish(@PathVariable String signature, @RequestBody AuctionFinishRequest request) throws BuysellException {
        return new ResponseEntity<>(auctionsService.finish(signature, request), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{signature}/views")
    public ResponseEntity<Integer> getViews(@PathVariable String signature) throws BuysellException {
        logger.debug("Getting auction views with signature {}", signature);

        return new ResponseEntity<>(auctionsService.getViews(signature), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{signature}/views/increment")
    public ResponseEntity<Integer> incrementViews(@PathVariable String signature, HttpServletRequest request) {
        return new ResponseEntity<>(auctionsService.incrementViews(signature, request.getRemoteAddr()), HttpStatus.OK);
    }

}
