package com.wosarch.buysell.common.services.mongo;

import co.elastic.clients.util.TriFunction;
import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.common.DatabaseObject;
import com.wosarch.buysell.common.model.mongo.DataCollectorConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class DataCollector<T extends DatabaseObject> {

    Logger logger = LoggerFactory.getLogger(DataCollector.class);

    private EntityManager entityManager;

    private Class<T> clazz;

    private Long lastObjectId;

    @Getter
    private int fetchedDocuments = 0;

    private final int pageSize;

    @Getter
    private final long total;

    private TriFunction<CriteriaBuilder, CriteriaQuery<?>, Root<?>, List<Predicate>> predicatesProvider;

    public DataCollector(DataCollectorConfig config) {
        this.entityManager = config.getEntityManager();
        this.clazz = config.getClazz();
        this.predicatesProvider = config.getPredicatesProvider();
        this.pageSize = config.getPageSize();
        this.total = getTotalDocuments();
    }

    public List<T> getData() {
        if (fetchedDocuments >= total) {
            fetchedDocuments = 0;
            return Collections.emptyList();
        }

        logger.info("[{}/{}] Fetching data...", fetchedDocuments, total);

        final TypedQuery<T> typedQuery = prepareQuery(pageSize);
        List<T> data = typedQuery.getResultList();
        lastObjectId = getLastObjectId(data);
        fetchedDocuments += data.size();
        logger.info("[{}/{}] Data fetched.", fetchedDocuments, total);

        return data;
    }

    private TypedQuery<T> prepareQuery(int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> root = criteriaQuery.from(clazz);
        List<Predicate> predicates = getPredicates(criteriaBuilder, criteriaQuery, root);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(DatabaseObject.Fields.OBJECT_ID)));

        return entityManager.createQuery(criteriaQuery)
                .setMaxResults(pageSize);
    }

    private long getTotalDocuments() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(criteriaBuilder.count(root));
        List<Predicate> predicates = predicatesProvider.apply(criteriaBuilder, criteriaQuery, root);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    private List<Predicate> getPredicates(CriteriaBuilder criteriaBuilder, CriteriaQuery<T> criteriaQuery, Root<T> root) {
        List<Predicate> newPredicates = new ArrayList<>();
        newPredicates.addAll(predicatesProvider.apply(criteriaBuilder, criteriaQuery, root));
        if (Objects.nonNull(lastObjectId)) {
            newPredicates.add(criteriaBuilder.greaterThan(root.get(DatabaseObject.Fields.OBJECT_ID), lastObjectId));
        }

        return newPredicates;
    }

    private Long getLastObjectId(List<T> data) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }

        T record = data.getLast();

        return record.getId();
    }
}
