package com.upversionlab.keugasto.category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vruzeda on 4/8/16.
 */
public class CategoryDAO {

    private static CategoryDAO instance = null;

    public static CategoryDAO getInstance() {
        if (instance == null) {
            instance = new CategoryDAO();
        }
        return instance;
    }

    private List<Category> categories;

    private CategoryDAO() {
        categories = new ArrayList<>();
    }

    public Category addCategory(String name, float limit) {
        Category category = new Category();
        category.name = name;
        category.limit = limit;
        categories.add(category);
        return category;
    }

    public List<Category> getCategories() {
        return categories;
    }
}
