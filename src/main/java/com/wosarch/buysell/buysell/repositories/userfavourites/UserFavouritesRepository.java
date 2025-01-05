package com.wosarch.buysell.buysell.repositories.userfavourites;

import com.wosarch.buysell.buysell.model.auctions.UserFavourites;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFavouritesRepository extends MongoRepository<UserFavourites, String>, TemplateUserFavouritesRepository {

    Optional<UserFavourites> findByUserId(String userId);

    default UserFavourites save(UserFavourites userFavourites) {
        return daoSave(userFavourites);
    }
}
