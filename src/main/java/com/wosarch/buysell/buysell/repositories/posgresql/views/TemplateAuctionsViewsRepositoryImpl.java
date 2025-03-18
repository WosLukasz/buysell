package com.wosarch.buysell.buysell.repositories.posgresql.views;

import com.wosarch.buysell.buysell.model.auctions.AuctionView;
import com.wosarch.buysell.buysell.repositories.posgresql.BuysellRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TemplateAuctionsViewsRepositoryImpl extends BuysellRepository implements TemplateAuctionsViewsRepository {

    private static final String COUNT_ALIAS = "count";
    private static final Integer DEFAULT_COUNT_VALUE = 0;

    @Override
    public Long getViews(String auctionSignature) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<AuctionView> root = criteriaQuery.from(AuctionView.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(criteriaBuilder.equal(root.get(AuctionView.Fields.AUCTION_SIGNATURE), auctionSignature));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    @Override
    @Transactional(value = "buySellTransactionManager")
    public Long incrementViews(String signature, String remoteAddress) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<AuctionView> root = criteriaQuery.from(AuctionView.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(
                criteriaBuilder.equal(root.get(AuctionView.Fields.AUCTION_SIGNATURE), signature),
                criteriaBuilder.equal(root.get(AuctionView.Fields.IP_ADDRESS), remoteAddress)
        );

        Long count = entityManager.createQuery(criteriaQuery).getSingleResult();

        if (count > 0) {
            return count;
        }

        entityManager.merge(new AuctionView(signature, remoteAddress));

        return count + 1;
    }
}
