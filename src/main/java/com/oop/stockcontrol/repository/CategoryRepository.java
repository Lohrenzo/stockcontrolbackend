package com.oop.stockcontrol.repository;

import com.oop.stockcontrol.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// The Repository Interface Is Responsible For Data Access
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // SELECT * FROM category where categoryId = ?
    @Query("SELECT s FROM Category s WHERE s.categoryId = ?1")
    Optional<Category> findCategoryById(Long categoryId);

    @Query("SELECT s FROM Category s WHERE s.categoryName = ?1")
    Optional<Category> findCategoryByName(String categoryName);
}
