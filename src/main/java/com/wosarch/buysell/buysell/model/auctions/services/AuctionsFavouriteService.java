package com.wosarch.buysell.buysell.model.auctions.services;

import com.wosarch.buysell.buysell.model.auctions.UserFavourites;

public interface AuctionsFavouriteService {

    UserFavourites getFavourites();

    UserFavourites addAuctionToFavourites(String auctionSignature);

    UserFavourites removeAuctionFromFavourites(String auctionSignature);

    UserFavourites updateFavouritesTopicality();
}
