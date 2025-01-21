package com.wosarch.buysell.buysell.repositories.mongo.userfavourites;

import com.wosarch.buysell.buysell.model.auctions.UserFavourites;
import com.wosarch.buysell.buysell.repositories.mongo.BuysellRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateUserFavouritesRepositoryImpl extends BuysellRepository implements TemplateUserFavouritesRepository {

    public UserFavourites daoSave(UserFavourites userFavourites) {
        return versionedSave(userFavourites, UserFavourites.COLLECTION_NAME);
    }

}
