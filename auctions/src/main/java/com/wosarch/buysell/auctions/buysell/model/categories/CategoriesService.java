package com.wosarch.buysell.auctions.buysell.model.categories;

import java.util.List;

public interface CategoriesService {

    List<Category> getAll();

    void parseStructure(String jsonRepresentation);

    void addCategory(String code, String parentId);

    void removeCategory(String code);
}
