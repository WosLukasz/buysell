package com.wosarch.buysell.buysell.repositories.posgresql.auctions;

import com.wosarch.buysell.buysell.model.auctions.Auction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionsRepository extends CrudRepository<Auction, Long>, TemplateAuctionsRepository {
}
