package com.wosarch.buysell.buysell.services.auctions;

import com.wosarch.buysell.admin.model.auth.RequestContextService;
import com.wosarch.buysell.buysell.model.auctions.AuctionsFavouriteService;
import com.wosarch.buysell.buysell.model.auctions.AuctionsService;
import com.wosarch.buysell.buysell.model.auctions.UserFavourites;
import com.wosarch.buysell.buysell.repositories.mongo.userfavourites.UserFavouritesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuctionsFavouriteServiceImpl implements AuctionsFavouriteService {

    @Autowired
    private UserFavouritesRepository userFavouritesRepository;

    @Autowired
    private RequestContextService requestContextService;

    @Autowired
    private AuctionsService auctionsService;

    @Override
    public UserFavourites getFavourites() {
        String userId = requestContextService.getCurrentUserId();

        return userFavouritesRepository.findByUserId(userId).orElse(createNewUserFavourites(userId));
    }

    @Override
    public UserFavourites addAuctionToFavourites(String auctionSignature) {
        String userId = requestContextService.getCurrentUserId();
        Optional<UserFavourites> userFavouritesOptional = userFavouritesRepository.findByUserId(userId);
        UserFavourites userFavourites = userFavouritesOptional.orElse(createNewUserFavourites(userId));
        if (CollectionUtils.isEmpty(userFavourites.getAuctions())) {
            userFavourites.setAuctions(new ArrayList<>());
        }

        userFavourites.getAuctions().add(auctionSignature);

        return userFavouritesRepository.save(userFavourites);
    }

    @Override
    public UserFavourites removeAuctionFromFavourites(String auctionSignature) {
        String userId = requestContextService.getCurrentUserId();
        Optional<UserFavourites> userFavouritesOptional = userFavouritesRepository.findByUserId(userId);
        if (userFavouritesOptional.isEmpty()) {
            throw new RuntimeException("Cannot remove auction from favourites when no favourites object for current user");
        }

        UserFavourites userFavourites = userFavouritesOptional.get();
        if (CollectionUtils.isEmpty(userFavourites.getAuctions())) {
            throw new RuntimeException("Cannot remove auction from favourites when no favourites auctions for current user");
        }

        userFavourites.getAuctions().removeIf(signature -> signature.equals(auctionSignature));

        return userFavouritesRepository.save(userFavourites);
    }

    @Override
    public UserFavourites updateFavouritesTopicality() {
        String userId = requestContextService.getCurrentUserId();
        Optional<UserFavourites> userFavouritesOptional = userFavouritesRepository.findByUserId(userId);
        if (userFavouritesOptional.isEmpty()) {
            return createNewUserFavourites(userId);
        }

        UserFavourites userFavourites = userFavouritesOptional.get();
        userFavourites.getAuctions()
                .removeIf(auctionSignature -> !auctionsService.auctionActive(auctionSignature));

        return userFavouritesRepository.save(userFavourites);
    }

    private UserFavourites createNewUserFavourites(String userId) {
        UserFavourites userFavourites = new UserFavourites();
        userFavourites.setUserId(userId);
        userFavourites.setAuctions(new ArrayList<>());

        return userFavourites;
    }
}
