package com.oop.stockcontrol.controller;

import com.oop.stockcontrol.entity.Category;
import com.oop.stockcontrol.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/categories")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get All Categories REST API
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    // Get Category By ID REST API
    @GetMapping(path = "details/{categoryId}")
    public Category getCategoryById(@PathVariable("categoryId") Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    // Add A New Category REST API
    @PostMapping(path = "create")
    public void addNewCategory(@RequestBody Category category) {
        categoryService.addNewCategory(category);
    }

    // Update A Category By ID REST API
    @PutMapping(path = "update/{categoryId}")
    public void updateCategory(
            @PathVariable("categoryId") Long categoryId,
            @RequestBody String categoryName
    ) {
        categoryService.updateCategory(categoryId, categoryName);
    }

    // Delete A Category By ID REST API
    @DeleteMapping(path = "delete/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }

}
