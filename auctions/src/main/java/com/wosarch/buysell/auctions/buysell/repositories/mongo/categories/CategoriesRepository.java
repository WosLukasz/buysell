package com.wosarch.buysell.auctions.buysell.repositories.mongo.categories;

import com.wosarch.buysell.auctions.buysell.model.categories.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends MongoRepository<Category, String>, TemplateCategoriesRepository {

    default Category save(Category category) {
        return daoSave(category);
    }
}
