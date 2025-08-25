package com.wosarch.buysell.auctions.buysell.patches;

import com.wosarch.buysell.auctions.common.model.patches.Patch;
import com.wosarch.buysell.auctions.common.services.elasticsearch.ElasticSearchIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class P20250120_1_indexes implements Patch {

    Logger logger = LoggerFactory.getLogger(P20250120_1_indexes.class);

    @Autowired
    private ElasticSearchIndexService elasticSearchIndexService;

    @Override
    public String getPatchId() {
        return "P20250120_1_index";
    }

    @Override
    public void run() {
        logger.info("[{}] Starts...", getPatchId());

        try {
            elasticSearchIndexService.upsertAllIndexes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        logger.info("[{}] Finished...", getPatchId());
    }
}