package com.oop.stockcontrol.service;

import com.oop.stockcontrol.entity.Category;
import com.oop.stockcontrol.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @MockBean
    private CategoryRepository categoryRepository;

//    @BeforeEach
//    void setup() {
//        Optional<Category> category = Optional.of(new Category((long) 1, "fruits"));
//        Mockito.when(categoryRepository.findById((long) 1)).thenReturn(category);
//    }
//
//    @Test
//    public void testFindCategoryById_Success() {
//        String categoryName = "fruits";
//        Category category = categoryService.getCategoryById((long) 1);
//        assertEquals(categoryName, category.getCategoryName());
//    }

    @Test
    public void getAllCategoriesTest() {
        Mockito.when(categoryRepository.findAll()).thenReturn(Stream
                .of(new Category(1, "fruits"), new Category(2, "soft drinks")).collect(Collectors.toList()));
        assertEquals(2, categoryService.getAllCategories().size());
    }

//    @Test
//    public void getCategoryByIdTest() {
//        long id = 1;
//
//        // Mocking the behavior of the categoryRepository
//        Mockito.when(categoryRepository.findById(id))
//                .thenReturn(Optional.of(new Category(id, "fruits")));
//
//        // Calling the service method
//        Category category = categoryService.getCategoryById(id);
//
//        // Assertions
//        assertNotNull(category);
//        assertEquals(id, category.getCategoryId());
//        assertEquals("fruits", category.getCategoryName());
//    }

    @Test
    public void getCategoryByNameTest() {
        String name = "Fruits";
        Mockito.when(categoryRepository.findCategoryByName(name)).thenReturn(Stream
                .of(new Category((long) 1, "Fruits")).collect(Collectors.toList()));
        assertEquals(1, categoryService.getCategoryByName(name).size());
    }
}