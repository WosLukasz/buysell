package com.wosarch.buysell.auctions.buysell.model.auctions;

public interface AuctionsFavouriteService {

    UserFavourites getFavourites();

    UserFavourites addAuctionToFavourites(String auctionSignature);

    UserFavourites removeAuctionFromFavourites(String auctionSignature);

    UserFavourites updateFavouritesTopicality();
}
