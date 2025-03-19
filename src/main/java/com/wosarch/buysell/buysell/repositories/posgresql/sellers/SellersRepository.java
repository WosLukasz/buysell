package com.wosarch.buysell.buysell.repositories.posgresql.sellers;

import com.wosarch.buysell.buysell.model.auctions.SellerProfile;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface SellersRepository extends CrudRepository<SellerProfile, Long>, TemplateSellersRepository {
}
