package com.wosarch.buysell.buysell.repositories.mongo.categories;

import com.wosarch.buysell.buysell.model.categories.Category;

public interface TemplateCategoriesRepository {

    Category daoSave(Category category);
}
