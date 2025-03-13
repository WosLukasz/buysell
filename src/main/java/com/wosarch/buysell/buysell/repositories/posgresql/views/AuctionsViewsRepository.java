package com.wosarch.buysell.buysell.repositories.posgresql.views;

import com.wosarch.buysell.buysell.model.auctions.AuctionView;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionsViewsRepository extends CrudRepository<AuctionView, Long>, TemplateAuctionsViewsRepository {

    Optional<AuctionView> findByAuctionSignature(String auctionSignature);
}
