package com.oop.stockcontrol.controller;

import com.oop.stockcontrol.entity.Product;
import com.oop.stockcontrol.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get All Products REST API
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Filter Products By Their Category ID REST API
    @GetMapping(path = "category/{categoryId}")
    public List<Product> getProductsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    // Get Products By Searching For Keyword In Description REST API
    @GetMapping(path = "description/{descriptionKeyword}")
    public List<Product> getProductsByDecsriptionSearch(@PathVariable("descriptionKeyword") String descriptionKeyword) {
        return productService.getProductsByDecsriptionSearch(descriptionKeyword);
    }

    // Get Product By ID REST API
    @GetMapping(path = "details/{productId}")
    public List<Product> getProductById(@PathVariable("productId") Long productId) {
        return productService.getProductById(productId);
    }

    // Get Product By SKU REST API
    @GetMapping(path = "details/{SKU}")
    public List<Product> getProductBySKU(@PathVariable("SKU") String SKU) {
        return productService.getProductBySKU(SKU);
    }

    // Add A New Product REST API
    @PostMapping(path = "create")
    public void addNewProduct(@RequestBody Product product) {
        productService.addNewProduct(product);
    }

    // Update A Product By ID REST API
    @PutMapping(path = "update/{productId}")
    public void updateProduct(@PathVariable("productId") Long productId, @RequestBody Product updatedProduct) {
        productService.updateProduct(productId, updatedProduct);
    }

    // Delete A Product By ID REST API
    @DeleteMapping(path = "delete/{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
    }

}
