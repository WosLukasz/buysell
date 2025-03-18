package com.wosarch.buysell.buysell.repositories.posgresql.auctions;

import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.repositories.posgresql.BuysellRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TemplateAuctionsRepositoryImpl extends BuysellRepository implements TemplateAuctionsRepository {

    public Optional<Auction> findBySignature(String signature) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Auction> criteriaQuery = criteriaBuilder.createQuery(Auction.class);
        Root<Auction> root = criteriaQuery.from(Auction.class);
        List<Predicate> predicates = List.of(criteriaBuilder.equal(root.get(Auction.Fields.SIGNATURE), signature));
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        criteriaQuery.select(root);
        Auction auction = entityManager.createQuery(criteriaQuery).getSingleResult();
        if (Objects.isNull(auction)) {
            return Optional.empty();
        }

        return Optional.of(auction);
    }

}
