package com.cdac.service;

import com.cdac.entities.Category;

import java.util.List;

public interface CategoryService {

    List<Category>getAllCategories();
    Category getCategoryI(Long id);
    Category saveCategory(Category category);
    void deleteCategory(Long category);
}
