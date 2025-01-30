package com.wosarch.buysell.buysell.services.categories;

import com.wosarch.buysell.buysell.model.categories.CategoriesService;
import com.wosarch.buysell.buysell.model.categories.Category;
import com.wosarch.buysell.buysell.repositories.mongo.categories.CategoriesRepository;
import com.wosarch.buysell.common.model.exception.BuysellException;
import com.wosarch.buysell.common.model.sequence.SequenceService;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    Logger logger = LoggerFactory.getLogger(CategoriesServiceImpl.class);

    private static final String CODE_PROP = "code";
    private static final String CHILDREN_PROP = "children";

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private SequenceService sequenceService;

    @Override
    public List<Category> getAll() {
        return categoriesRepository.findAll();
    }

    @Override
    public void parseStructure(String jsonRepresentation) {
        logger.info("Start Processing categories...");
        if (StringUtils.isEmpty(jsonRepresentation)) {
            throw new BuysellException("No categories json passed.");
        }

        categoriesRepository.deleteAll();
        final JSONObject root = new JSONObject(jsonRepresentation);
        processCategoryAndChildren(root, null);

        logger.info("Stop Processing categories...");
    }

    @Override
    public void addCategory(String code, String parentId) {
        // not implemented yet
    }

    @Override
    public void removeCategory(String code) {
        // not implemented yet
    }

    private void processCategoryAndChildren(JSONObject categoryJson, String parentId) {
        String code = categoryJson.getString(CODE_PROP);
        Category savedCategory = saveCategory(code, parentId);
        final JSONArray children = categoryJson.getJSONArray(CHILDREN_PROP);
        final int n = children.length();
        if (n == 0) {
            return;
        }

        for (int i = 0; i < n; ++i) {
            final JSONObject nextCategoryJson = children.getJSONObject(i);
            processCategoryAndChildren(nextCategoryJson, savedCategory.getId());
        }
    }

    private Category saveCategory(String code, String parentId) {
        logger.info("Save new category {} with parent {}", code, parentId);
        Category category = new Category();
        category.setId(sequenceService.getNext(Category.SEQUENCE_NAME));
        category.setCode(code);
        category.setParentId(parentId);

        return categoriesRepository.save(category);
    }
}
