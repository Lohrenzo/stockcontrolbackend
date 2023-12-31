package com.oop.stockcontrol.service;

import com.oop.stockcontrol.entity.Category;
import com.oop.stockcontrol.entity.Product;
import com.oop.stockcontrol.repository.CategoryRepository;
import com.oop.stockcontrol.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Get All Products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get Product By Product ID
    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        // If the product is present, return it
        return productOptional.orElseThrow(() -> new IllegalStateException("Product with Id: " + productId + " does not exist."));

//        if (productOptional.isPresent()) {
//            productRepository.findById(productId);
//        }
//        throw new IllegalStateException("Product with Id: " + productId + " does not exist.");
    }

    public List<Product> getProductBySKU(String SKU) {
        Optional<Product> productOptional = productRepository.findProductBySKU(SKU);

        if (productOptional.isPresent()) {
            productRepository.findProductBySKU(SKU);
        }
        throw new IllegalStateException("Product with SKU: " + SKU + " does not exist.");
    }

    // Get Products By Category ID
    public List<Product> getProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findProductsByCategoryId(categoryId);

        if (products.isEmpty()) {
            throw new IllegalStateException("No Products Found For Category with Id: " + categoryId);
        }

        return products;
    }

    // Get Products By Searching For Matching Keywords In Description
    public List<Product> getProductsByDescriptionSearch(String descriptionKeyword) {
        List<Product> products = productRepository.findProductsByDescriptionSearch(descriptionKeyword);

        if (products.isEmpty()) {
            throw new IllegalStateException("Products with keyword: '" + descriptionKeyword + "' in its description does not exist.");
        }

        return products;
    }

    // Add A New Product
    public void addNewProduct(Product product) {
        Optional<Product> productOptional = productRepository.findProductByName(product.getName());

        if (productOptional.isPresent()) {
            throw new IllegalStateException("Product Already Exists.");
        }

        // Ensure the category is managed
        Optional<Category> categoryOptional = categoryRepository.findById(product.getCategory().getCategoryId());
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            try {
                product.setCategory(category);
                productRepository.save(product);
            } catch (IllegalStateException e) {
                System.out.println("Failed to add the product: " + e.getMessage());
            }
        } else {
            throw new IllegalStateException("Category with ID: " + product.getCategory().getCategoryId() + " not found.");
        }

    }

    public void addProductViaMenu(Product product) {
//        Optional<Product> productOptional = productRepository.findProductByName(product.getName());
//
//        if (productOptional.isPresent()) {
//            throw new IllegalStateException("Product Already Exists.");
//        }
//
//        // Ensure the category is managed
//        Category category = categoryRepository.findById(product.getCategory().getCategoryId())
//                .orElseThrow(() -> new IllegalStateException("Category with ID: " + product.getCategory().getCategoryId() + " not found."));
//
//        product.setCategory(category);
        productRepository.save(product);
    }


    // Update A Product By ID
    @Transactional
    public void updateProduct(Long productId, Product updatedProduct) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product with Id: " + productId + " does not exist."));

        // Update only non-null fields from updatedProduct
        if (updatedProduct.getSKU() != null) {
            product.setSKU(updatedProduct.getSKU());
        } else {
            product.setSKU(product.getSKU());
        }

        if (updatedProduct.getName() != null) {
            product.setName(updatedProduct.getName());
        } else {
            product.setName(product.getName());
        }

        if (updatedProduct.getDescription() != null) {
            product.setDescription(updatedProduct.getDescription());
        } else {
            product.setDescription(product.getDescription());
        }

        if (updatedProduct.getPrice() != 0) {
            product.setPrice(updatedProduct.getPrice());
        } else {
            product.setPrice(product.getPrice());
        }

        if (updatedProduct.getAvailableQuantity() != null) {
            product.setAvailableQuantity(updatedProduct.getAvailableQuantity());
        } else {
            product.setAvailableQuantity(product.getAvailableQuantity());
        }

        if (updatedProduct.getCategory() != null) {
            product.setCategory(updatedProduct.getCategory());
        } else {
            product.setCategory(product.getCategory());
        }
    }

    public void updateProductViaMenu(Long productId, Product updatedProduct) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product with Id: " + productId + " does not exist."));

        // Update only non-null fields from updatedProduct
        if (updatedProduct.getSKU() != null) {
            product.setSKU(updatedProduct.getSKU());
        } else {
            product.setSKU(product.getSKU());
        }

        if (updatedProduct.getName() != null) {
            product.setName(updatedProduct.getName());
        } else {
            product.setName(product.getName());
        }

        if (updatedProduct.getDescription() != null) {
            product.setDescription(updatedProduct.getDescription());
        } else {
            product.setDescription(product.getDescription());
        }

        if (updatedProduct.getPrice() != 0) {
            product.setPrice(updatedProduct.getPrice());
        } else {
            product.setPrice(0);
        }

        if (updatedProduct.getCategory() != null) {
            product.setCategory(updatedProduct.getCategory());
        } else {
            product.setCategory(product.getCategory());
        }
    }

//    public void updateProduct(Long productId, String SKU, String name, String description, Integer price) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(
//                        () -> new IllegalStateException("Product with Id: " + productId + " does not exist.")
//                );
//
//        if (SKU != null && !SKU.isEmpty() && !Objects.equals(product.getSKU(), SKU)) {
//            Optional<Product> productOptional = productRepository.findProductBySKU(SKU);
//            if (productOptional.isPresent()) {
//                throw new IllegalStateException("Product With SKU: " + SKU + " is already taken");
//            }
//            product.setSKU(SKU);
//        }
//
//        if (name != null && !name.isEmpty() && !Objects.equals(product.getName(), name)) {
//            Optional<Product> productOptional = productRepository.findProductByName(name);
//            if (productOptional.isPresent()) {
//                throw new IllegalStateException("Product With Name: " + name + " is already taken");
//            }
//            product.setName(name);
//        }
//
//        product.setDescription(description);
//
//        if (price != null && !(price <= 0) && !Objects.equals(product.getPrice(), price)) {
//            product.setPrice(price);
//        }
//    }

    // Delete A Product By ID
    public void deleteProduct(Long productId) {
        boolean exists = productRepository.existsById(productId);
        if (!exists) {
            throw new IllegalStateException("Product with id: " + productId + " does not exist.");
        }
        productRepository.deleteById(productId);
    }

}
