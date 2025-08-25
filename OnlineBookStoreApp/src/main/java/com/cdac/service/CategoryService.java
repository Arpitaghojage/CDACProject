package com.cdac.service;

import com.cdac.dto.CategoryReqDTO;
import com.cdac.dto.CategoryRespDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryRespDTO>getAllCategories();
    CategoryRespDTO getCategoryName(String categoryName);
    CategoryRespDTO saveCategory(CategoryReqDTO categoryDto);
    void deleteCategory(String categoryName);
}
