package com.wosarch.buysell.buysell.model.auctions;

public interface AuctionsFavouriteService {

    UserFavourites getFavourites();

    UserFavourites addAuctionToFavourites(String auctionSignature);

    UserFavourites removeAuctionFromFavourites(String auctionSignature);

    UserFavourites updateFavouritesTopicality();
}
