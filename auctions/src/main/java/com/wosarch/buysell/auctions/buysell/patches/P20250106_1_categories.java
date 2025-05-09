package com.wosarch.buysell.auctions.buysell.patches;

import com.wosarch.buysell.auctions.common.model.patches.Patch;
import com.wosarch.buysell.auctions.common.utils.ResourceReader;
import com.wosarch.buysell.auctions.buysell.model.categories.CategoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class P20250106_1_categories implements Patch {

    Logger logger = LoggerFactory.getLogger(P20250106_1_categories.class);

    @Autowired
    private CategoriesService categoriesService;

    @Override
    public String getPatchId() {
        return "P20250106_1_categories";
    }

    @Override
    public void run() {
        logger.info("[{}] Starts...", getPatchId());

        categoriesService.parseStructure(ResourceReader.readFileToString("classpath:categories.json"));

        logger.info("[{}] Finished...", getPatchId());
    }
}