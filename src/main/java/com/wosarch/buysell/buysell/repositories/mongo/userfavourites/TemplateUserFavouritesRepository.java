package com.wosarch.buysell.buysell.repositories.mongo.userfavourites;

import com.wosarch.buysell.buysell.model.auctions.UserFavourites;

public interface TemplateUserFavouritesRepository {

    UserFavourites daoSave(UserFavourites userFavourites);
}
