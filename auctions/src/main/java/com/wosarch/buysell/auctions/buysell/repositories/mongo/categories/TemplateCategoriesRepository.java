package com.wosarch.buysell.auctions.buysell.repositories.mongo.categories;

import com.wosarch.buysell.auctions.buysell.model.categories.Category;

public interface TemplateCategoriesRepository {

    Category daoSave(Category category);
}
