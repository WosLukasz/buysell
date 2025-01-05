package com.wosarch.buysell.buysell.api.auctions;

import com.wosarch.buysell.admin.model.auth.RequestContextService;
import com.wosarch.buysell.buysell.model.auctions.AuctionsFavouriteService;
import com.wosarch.buysell.buysell.model.auctions.UserFavourites;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auctions-favourites")
public class AuctionsFavouritesServiceRestEndpoint {

    Logger logger = LoggerFactory.getLogger(AuctionsFavouritesServiceRestEndpoint.class);

    @Autowired
    private AuctionsFavouriteService auctionsFavouriteService;

    @Autowired
    private RequestContextService requestContextService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<UserFavourites> getFavourite() {
        logger.debug("Get favourites for user {}", requestContextService.getCurrentUserId());

        return new ResponseEntity<>(auctionsFavouriteService.getFavourites(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.PUT, path = "/refresh")
    public ResponseEntity<UserFavourites> refreshFavourites() {
        logger.debug("Refreshing favourites for user {}", requestContextService.getCurrentUserId());

        return new ResponseEntity<>(auctionsFavouriteService.updateFavouritesTopicality(), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.POST, path = "/{signature}")
    public ResponseEntity<UserFavourites> addAsFavourite(@PathVariable String signature) {
        logger.debug("Adding auction {} to favourites to user {}", signature, requestContextService.getCurrentUserId());

        return new ResponseEntity<>(auctionsFavouriteService.addAuctionToFavourites(signature), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(method = RequestMethod.DELETE, path = "/{signature}")
    public ResponseEntity<UserFavourites> removeFromFavourite(@PathVariable String signature) {
        logger.debug("Removing auction {} from favourites to user {}", signature, requestContextService.getCurrentUserId());

        return new ResponseEntity<>(auctionsFavouriteService.removeAuctionFromFavourites(signature), HttpStatus.OK);
    }

}