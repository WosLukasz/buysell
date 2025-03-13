package com.wosarch.buysell.common.model.mongo;

import co.elastic.clients.util.TriFunction;
import com.wosarch.buysell.buysell.model.auctions.Auction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.concurrent.Callable;

@Data
@Builder
public class DataCollectorConfig {

    private EntityManager entityManager;

    private Class clazz;

    private TriFunction<CriteriaBuilder, CriteriaQuery<?>, Root<?>, List<Predicate>> predicatesProvider;

    private int pageSize;
}
