package com.wosarch.buysell.buysell.api.auctions;

import com.wosarch.buysell.buysell.model.auctions.*;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionCreationRequest;
import com.wosarch.buysell.buysell.model.auctions.requests.AuctionFinishRequest;
import com.wosarch.buysell.common.model.attachments.AttachmentWithContent;
import com.wosarch.buysell.common.model.exception.BuysellException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auctions")
public class AuctionsServiceRestEndpoint {

    Logger logger = LoggerFactory.getLogger(AuctionsServiceRestEndpoint.class);

    @Autowired
    private AuctionsService auctionsService;

    @Autowired
    private AuctionAttachmentsService auctionAttachmentsService;

    @RequestMapping(method = RequestMethod.GET, path = "/{signature}")
    public ResponseEntity<Auction> get(@PathVariable String signature) throws BuysellException {
        logger.debug("Getting auction with signature {}", signature);

        return new ResponseEntity<>(auctionsService.get(signature), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Auction> create(@RequestBody @Valid AuctionCreationRequest request) {
        logger.debug("Creating new auction with title {}", request.getTitle());

        return new ResponseEntity<>(auctionsService.create(request), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<Auction> save(@RequestBody Auction auction) {
        logger.debug("Saving auction with signature {}", auction.getSignature());

        return new ResponseEntity<>(auctionsService.save(auction), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.PUT, path = "/{signature}/finish")
    public ResponseEntity<Auction> finish(@PathVariable String signature, @RequestBody AuctionFinishRequest request) throws BuysellException {
        logger.debug("Finishing auction with signature {}", signature);

        return new ResponseEntity<>(auctionsService.finish(signature, request), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.PUT, path = "/{signature}/refresh")
    public ResponseEntity<Auction> refresh(@PathVariable String signature) {
        logger.debug("Refreshing auction with signature {}", signature);

        return new ResponseEntity<>(auctionsService.refresh(signature), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{signature}/attachments")
    public ResponseEntity<List<AttachmentWithContent>> getAuctionAttachmentsWithContent(@PathVariable String signature) throws BuysellException {
        logger.debug("Getting auction attachments for auction with signature {}", signature);

        return new ResponseEntity<>(auctionsService.getAuctionAttachmentsWithContent(signature), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{signature}/views")
    public ResponseEntity<Integer> getViews(@PathVariable String signature) {
        logger.debug("Getting auction views with signature {}", signature);

        return new ResponseEntity<>(auctionsService.getViews(signature), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/{signature}/views/increment")
    public ResponseEntity<Integer> incrementViews(@PathVariable String signature, HttpServletRequest request) {
        return new ResponseEntity<>(auctionsService.incrementViews(signature, request.getRemoteAddr()), HttpStatus.OK);
    }
}
