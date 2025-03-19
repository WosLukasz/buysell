package com.wosarch.buysell.buysell.repositories.posgresql.categories;

import com.wosarch.buysell.buysell.model.categories.Category;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@JaversSpringDataAuditable
public interface CategoriesRepository extends CrudRepository<Category, Long>, TemplateCategoriesRepository {

    List<Category> findAll();

}
