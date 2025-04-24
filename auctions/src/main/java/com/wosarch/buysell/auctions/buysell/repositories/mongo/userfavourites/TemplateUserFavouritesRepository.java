package com.wosarch.buysell.auctions.buysell.repositories.mongo.userfavourites;

import com.wosarch.buysell.auctions.buysell.model.auctions.UserFavourites;

public interface TemplateUserFavouritesRepository {

    UserFavourites daoSave(UserFavourites userFavourites);
}
