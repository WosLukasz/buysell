package com.wosarch.buysell.buysell.repositories.posgresql.auctionssignaturesequence;

import com.wosarch.buysell.buysell.model.auctions.AuctionSignatureSequence;
import com.wosarch.buysell.buysell.repositories.posgresql.auctions.TemplateAuctionsRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionsSignatureSequenceRepository extends CrudRepository<AuctionSignatureSequence, Long>, TemplateAuctionsRepository {
}
