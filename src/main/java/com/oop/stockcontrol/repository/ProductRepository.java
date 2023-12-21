package com.oop.stockcontrol.repository;

import com.oop.stockcontrol.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// The Repository Interface Is Responsible For Data Access
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // SELECT * FROM product where productId = ?
    @Query("SELECT p FROM Product p WHERE p.productId = ?1")
    Optional<Product> findProductById(Long productId);

    @Query("SELECT p FROM Product p WHERE p.name = ?1")
    Optional<Product> findProductByName(String name);

    @Query("SELECT p FROM Product p WHERE p.SKU = ?1")
    Optional<Product> findProductBySKU(String SKU);

    @Query("SELECT p FROM Product p WHERE p.category.categoryId = ?1")
    List<Product> findProductsByCategoryId(Long categoryId);

    @Query("SELECT p FROM Product p WHERE LOWER(p.description) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Product> findProductsByDecsriptionSearch(String descriptionKeyword);
}
