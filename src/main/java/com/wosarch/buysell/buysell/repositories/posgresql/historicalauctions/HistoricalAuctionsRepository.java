package com.wosarch.buysell.buysell.repositories.posgresql.historicalauctions;

import com.wosarch.buysell.buysell.model.auctions.HistoricalAuction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricalAuctionsRepository extends CrudRepository<HistoricalAuction, Long>, TemplateHistoricalAuctionsRepository {
}
