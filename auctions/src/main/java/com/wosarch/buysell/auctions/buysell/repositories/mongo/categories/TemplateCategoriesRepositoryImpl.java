package com.wosarch.buysell.auctions.buysell.repositories.mongo.categories;

import com.wosarch.buysell.auctions.buysell.model.categories.Category;
import com.wosarch.buysell.auctions.buysell.repositories.mongo.BuysellRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateCategoriesRepositoryImpl extends BuysellRepository implements TemplateCategoriesRepository {

    public Category daoSave(Category category) {
        return versionedSave(category, Category.COLLECTION_NAME);
    }

}
