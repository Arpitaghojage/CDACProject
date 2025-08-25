package com.cdac.controller;

import com.cdac.dto.CategoryReqDTO;
import com.cdac.dto.CategoryRespDTO;
import com.cdac.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryRespDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryName}")
    public ResponseEntity<CategoryRespDTO> getCategoryName(@PathVariable String categoryName) {
        return ResponseEntity.ok(categoryService.getCategoryName(categoryName));
    }

    @PostMapping
    public ResponseEntity<CategoryRespDTO> saveCategory(@RequestBody CategoryReqDTO categoryDto) {
        return ResponseEntity.ok(categoryService.saveCategory(categoryDto));
    }

    @DeleteMapping("/{categoryName}")
    public ResponseEntity<String> deleteCategory(@PathVariable String categoryName) {
        categoryService.deleteCategory(categoryName);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
