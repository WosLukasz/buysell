package com.wosarch.buysell.buysell.repositories.posgresql.userfavourites;

import com.wosarch.buysell.buysell.model.auctions.UserFavourites;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserFavouritesRepository extends CrudRepository<UserFavourites, Long>, TemplateUserFavouritesRepository {

    Optional<UserFavourites> findByUserId(String userId);

}
