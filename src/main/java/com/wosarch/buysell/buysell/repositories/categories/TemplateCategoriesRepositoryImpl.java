package com.wosarch.buysell.buysell.repositories.categories;

import com.wosarch.buysell.buysell.model.categories.Category;
import com.wosarch.buysell.buysell.repositories.BuysellRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TemplateCategoriesRepositoryImpl extends BuysellRepository implements TemplateCategoriesRepository {

    public Category daoSave(Category category) {
        return versionedSave(category, Category.COLLECTION_NAME);
    }

}
