package com.wosarch.buysell.buysell.repositories.posgresql.sellers;

import com.wosarch.buysell.buysell.model.auctions.SellerProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellersRepository extends CrudRepository<SellerProfile, Long>, TemplateSellersRepository {
}
