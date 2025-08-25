package com.cdac.service;

import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dto.CategoryReqDTO;
import com.cdac.dto.CategoryRespDTO;
import com.cdac.entities.Category;
import com.cdac.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CategoryServiceImpl  implements CategoryService{

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Override
    public List<CategoryRespDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryRespDTO.class))
                .collect(Collectors.toList());
    }



    @Override
    public CategoryRespDTO getCategoryName(String categoryName) {
        Category category =  categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryName));
        return modelMapper.map(category, CategoryRespDTO.class);
    }

    @Override
    public CategoryRespDTO saveCategory(CategoryReqDTO categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category saved = categoryRepository.save(category);
        return modelMapper.map(saved, CategoryRespDTO.class);
    }

    @Override
    public void deleteCategory(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with CName " + categoryName));
        categoryRepository.delete(category);
    }
}
