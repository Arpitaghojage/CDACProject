package com.cdac.service;

import com.cdac.dto.CategoryReqDTO;
import com.cdac.dto.CategoryRespDTO;
import com.cdac.entities.Category;

import java.util.List;

public interface CategoryService {

    List<CategoryRespDTO>getAllCategories();
    CategoryRespDTO getCategoryById(Long id);
    CategoryRespDTO saveCategory(CategoryReqDTO categoryDto);
    void deleteCategory(Long id);
}
