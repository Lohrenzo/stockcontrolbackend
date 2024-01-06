package com.oop.stockcontrol.service;

import com.oop.stockcontrol.entity.Category;
import com.oop.stockcontrol.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Get All Categories
    public List<Category> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        System.out.println("Getting Data from DB: " + categoryList);
        return categoryList;
    }

    // Get Category By Category ID
//    public Category getCategoryById(Long categoryId) {
//        Optional<Category> categoryOptional = categoryRepository.getCategoryById(categoryId);
//
//        if (categoryOptional.isPresent()) {
//            categoryRepository.findById(categoryId);
//        }
//        throw new IllegalStateException("Category with Id: " + categoryId + " does not exist.");
//    }
    public Category getCategoryById(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.getCategoryById(categoryId);

        return categoryOptional.orElseThrow(() ->
                new IllegalStateException("Category with Id: " + categoryId + " does not exist.")
        );
    }

    public Category getCategoryByName(String name) {
        Optional<Category> categoryOptional = categoryRepository.findCategoryByName(name);

        if (categoryOptional.isPresent()) {
            return categoryOptional.get();
        }

        throw new IllegalStateException("Category With Such Name: " + name + " Does Not Exist.");
    }

    // Add A New Category
    public void addNewCategory(Category category) {
        Optional<Category> categoryOptional = categoryRepository.findCategoryByName(category.getCategoryName());

        if (categoryOptional.isPresent()) {
            throw new IllegalStateException("Category Already Exists.");
        }
        categoryRepository.save(category);
    }

    // Update A Category By ID
    @Transactional
    public void updateCategory(Long categoryId, String categoryName) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new IllegalStateException("Category with Id: " + categoryId.toString() + " does not exist.")
                );

        if (categoryName != null && !categoryName.isEmpty() && !Objects.equals(category.getCategoryName(), categoryName)) {
            Optional<Category> categoryOptional = categoryRepository.findCategoryByName(categoryName);
            if (categoryOptional.isPresent()) {
                throw new IllegalStateException("Category With Name: " + categoryName + " already exists.");
            }
            category.setCategoryName(categoryName);
        }
    }

    // Delete A Category By ID
    public void deleteCategory(Long categoryId) {
        boolean exists = categoryRepository.existsById(categoryId);
        if (!exists) {
            throw new IllegalStateException("Category with Id: " + categoryId + " does not exist.");
        }
        categoryRepository.deleteById(categoryId);
    }
}
