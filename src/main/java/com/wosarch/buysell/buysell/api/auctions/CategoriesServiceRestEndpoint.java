package com.wosarch.buysell.buysell.api.auctions;

import com.wosarch.buysell.buysell.model.categories.CategoriesService;
import com.wosarch.buysell.buysell.model.categories.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesServiceRestEndpoint {

    Logger logger = LoggerFactory.getLogger(CategoriesServiceRestEndpoint.class);

    @Autowired
    private CategoriesService categoriesService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getCategories() {

        return new ResponseEntity<>(categoriesService.getAll(), HttpStatus.OK);
    }
}