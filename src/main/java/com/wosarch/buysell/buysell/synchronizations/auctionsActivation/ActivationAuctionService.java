package com.wosarch.buysell.buysell.synchronizations.auctionsActivation;

import co.elastic.clients.util.TriFunction;
import com.wosarch.buysell.buysell.model.auctions.Auction;
import com.wosarch.buysell.buysell.model.auctions.enums.AuctionStatus;
import com.wosarch.buysell.buysell.model.auctions.services.AuctionsService;
import com.wosarch.buysell.common.model.mongo.DataCollectorConfig;
import com.wosarch.buysell.common.services.mongo.DataCollector;
import com.wosarch.buysell.common.services.parallel.CompletionIterator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;

@Service
public class ActivationAuctionService {

    Logger logger = LoggerFactory.getLogger(ActivationAuctionService.class);

    private static final Integer BATCH_SIZE = 100;

    private static final Integer PROCESSING_THREADS_POOL_SIZE = 4;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AuctionsService auctionsService;

    public void processAuctions() throws Exception {
        DataCollectorConfig config = DataCollectorConfig.builder()
                .entityManager(entityManager)
                .clazz(Auction.class)
                .predicatesProvider(preparePredicates())
                .pageSize(BATCH_SIZE)
                .build();

        DataCollector collector = new DataCollector(config);
        List<Auction> dataToProcess = collector.getData();
        if (CollectionUtils.isEmpty(dataToProcess)) {
            logger.info("No auctions to activate.");
            return;
        }

        try (CompletionIterator<Void> completionIterator = new CompletionIterator<>(PROCESSING_THREADS_POOL_SIZE)) {
            while (!CollectionUtils.isEmpty(dataToProcess)) {
                dataToProcess.forEach(auctionToActivate -> {
                    ActivationAuctionJob task = ActivationAuctionJob.builder()
                            .auction(auctionToActivate)
                            .auctionsService(auctionsService)
                            .logger(logger)
                            .build();
                    completionIterator.submit(task);
                });

                completionIterator.waitWithoutResponses();
                dataToProcess = collector.getData();
            }
        } catch (Exception e) {
            logger.error("Error occurred during auctions activation", e);
            throw e;
        }

        logger.info("All auctions activated");
    }

    private TriFunction<CriteriaBuilder, CriteriaQuery<?>, Root<?>, List<Predicate>> preparePredicates() {
        return (CriteriaBuilder criteriaBuilder, CriteriaQuery<?> criteriaQuery, Root<?> root) ->
            List.of(criteriaBuilder.equal(root.get(Auction.Fields.STATUS).as(String.class), AuctionStatus.QUEUED.name()));
    }
}
