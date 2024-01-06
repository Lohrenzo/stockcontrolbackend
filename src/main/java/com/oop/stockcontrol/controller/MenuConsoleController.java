package com.oop.stockcontrol.controller;

import com.oop.stockcontrol.entity.Category;
import com.oop.stockcontrol.entity.Product;
import com.oop.stockcontrol.entity.User;
import com.oop.stockcontrol.enums.UserRole;
import com.oop.stockcontrol.repository.CategoryRepository;
import com.oop.stockcontrol.repository.ProductRepository;
import com.oop.stockcontrol.repository.UserRepository;
import com.oop.stockcontrol.service.CategoryService;
import com.oop.stockcontrol.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class MenuConsoleController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    @Autowired
    public MenuConsoleController(
            ProductService productService,
            ProductRepository productRepository,
            CategoryService categoryService,
            CategoryRepository categoryRepository,
            UserRepository userRepository
    ) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    public int showMenu() {
        Scanner scanner = new Scanner(System.in);
        int option;

        while (true) {
            try {
                System.out.println("-----------------------------------------------------");
                System.out.println("\t\t The Food Store");
                System.out.print("Choose from the following options: \n");
                System.out.println("------------------------------------------------------\n");
                System.out.println("[1] List All Products");
                System.out.println("[2] Search For A Product By ID");
                System.out.println("[3] Add A New Product");
                System.out.println("[4] Update Product By ID");
                System.out.println("[5] Delete Product By ID");
                System.out.println("[6] Exit");

                // Validate input to ensure it's an integer
                option = Integer.parseInt(scanner.nextLine());
                break; // Break out of the loop if parsing is successful
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid option.");
            }
        }
        return option;
    }

    // View All Products
    public void getAllProducts() {
        while (true) {
            System.out.println("All Products!");
            System.out.println("________________________________________________");
            System.out.println("ID | Name | Description | Category | Price ");
            System.out.println("________________________________________________");
            List<Product> products = productService.getAllProducts();
            for (Product product : products) {
                String productString = product.getProductId() + " | " + product.getSKU() + " | " + product.getName() + " | " + product.getDescription()
                        + " | "
                        + product.getCategory().getCategoryName() + " | " + product.getPrice();
                System.out.println(productString);
            }
            System.out.println("Input '0' to EXIT: ");

            Scanner input = new Scanner(System.in);
            long nextId = Long.parseLong(input.nextLine());
            if (nextId == 0) {
                break;  // Exit the loop if '0' is entered
            }
        }
    }

    // View A Product By ID Provided
    public void getProductById() {

        // Retrieve a product by its ID
        Scanner input = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\nGet Product By ID");
                System.out.println("________________________________________________");
                System.out.println("Input A Product's ID or Input '0' to EXIT!: ");
                long id = Long.parseLong(input.nextLine());

                if (id == 0) {
                    break;  // Exit the loop if '0' is entered
                }

                Product product = productService.getProductById(id);

                if (product != null) {
                    System.out.println(product);
                    System.out.println("________________________________________________");
                }
                else {
                    System.out.println("Product ID Cannot be Empty!!");
                }

            } catch (IllegalStateException | NumberFormatException e) {
                System.out.println("Invalid input. Please Enter A Valid Product ID.");
            } finally {
                input.nextLine();  // Consume the newline character
            }
        }
    }

    // Add a New Product
    public void addNewProduct() {
        Scanner input = new Scanner(System.in);
        System.out.println("Add A New Product!");
        System.out.println("________________________________________________");

        System.out.println("Only Admins Are Allowed. I'd like to know your username.\n");
        // Check if user is Admin
        System.out.println("Enter Username: ");
        String username = input.nextLine();
        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new IllegalStateException("Username does not exist")
        );

        if (user.getRole().equals(UserRole.ADMIN)) {

            try (input) {
                System.out.print("Enter product SKU: ");
//                String SKU = StringUtils.upperCase(input.nextLine());
                String SKU = input.nextLine().toUpperCase();
                System.out.print("Enter product name: ");
                String name = StringUtils.capitalize(input.nextLine());
                System.out.print("Enter product description: ");
                String description = input.nextLine();
                System.out.print("Enter product quantity: ");
                Long quantity = Long.parseLong(input.nextLine());

                // Display existing categories
                List<Category> existingCategories = categoryService.getAllCategories();
                System.out.println("Existing Categories: ");
                for (Category category : existingCategories) {
                    System.out
                            .println("Category ID: " + category.getCategoryId() + " | Category Name: " + category.getCategoryName());
                }

                // Prompt user to choose a Category ID
                long categoryId;
                while (true) {
                    while (true) {
                        try {
                            System.out.print("Enter Product Category (Choose From Existing Category IDs) Or Input 0 to EXIT!: ");
//                            System.out.print("Enter Product Price: ");
                            categoryId = Long.parseLong(input.nextLine());
                            break; // Break out of the loop if parsing is successful
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please Enter A Number Value.");
                        }
                    }

                    if (categoryId == 0) {
                        return; // Exit the method if '0' is entered
                    }

                    // Check if the inputted categoryId exists
                    Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

                    if (categoryOptional.isPresent()) {
                        double price;
                        while (true) {
                            try {
                                System.out.print("Enter Product Price: ");
                                price = Double.parseDouble(input.nextLine());
                                break; // Break out of the loop if parsing is successful
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please Enter A Valid Price.");
                            }
                        }

                        // Fetch the category object
                        Category category = categoryOptional.get();

                        Optional<Product> existingProduct = productRepository.findProductByName(name);
                        if (existingProduct.isPresent()) {
                            System.out.println("Product with the same name already exists.");
                            return;  // Do not proceed with adding a new product
                        } else {
                            // Attempt to add the new product
                            try {
                                Product newProduct = new Product(SKU, category, description, price, name, quantity);
                                productService.addProductViaMenu(newProduct);
                                System.out.println("Product '" + name + "' Has Been Added!");
                            } catch (Exception e) {
                                System.out.println("Product '" + name + "' Not Added!!");
                                return;
                            }
                        }

                    } else {
                        System.out.println("Category with ID: " + categoryId + " not found.");
                    }

                }
            }

        } else {
            System.out.println("Only Admins Are Allowed");
        }
        input.close();
    }


    // Update A Product By ID
    public void updateProductById() {
        Scanner input = new Scanner(System.in);

        boolean validProductId = false;

        while (!validProductId) {
            System.out.println("Update A Product By ID");
            System.out.println("________________________________________________");
            System.out.println("Enter Product ID: ");

            try {
                Long productId = Long.parseLong(input.nextLine());

                // Check if product exists
                Optional<Product> productOptional = productRepository.findById(productId);

                // If it exists, proceed with the update
                if (productOptional.isPresent()) {
                    Product product = productOptional.get();
                    System.out.println(product);
                    System.out.println(productOptional);
                    System.out.println("Please Enter Name: ");
                    String name = input.nextLine();
                    System.out.println("Please Enter SKU: ");
                    String sku = input.nextLine();
                    System.out.println("Please Enter Description: ");
                    String description = input.nextLine();
                    System.out.print("Enter product quantity: ");
                    Long quantity = Long.parseLong(input.nextLine());

                    displayAllCategories();
                    System.out.print("Enter Product Category (Choose From Existing Category IDs): ");
                    Long categoryId = Long.parseLong(input.nextLine());

                    // Check if the inputted categoryId exists
                    Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
                    if (categoryOptional.isEmpty()) {
                        System.out.println("Category With ID: " + categoryId + " Does Not Exist.");
                        System.out.println("Choose A Valid Category ID.");
                        displayAllCategories();
                        continue;  // Continue to the next iteration of the loop
                    }

                    double price;
                    while (true) {
                        try {
                            System.out.print("Please Enter Price: ");
                            price = Double.parseDouble(input.nextLine());
                            break; // Break out of the loop if parsing is successful
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please Enter A Valid Price.");
                        }
                    }

                    // Create an updated product
                    Product updatedProduct = new Product(sku, categoryOptional.get(), description, price, name, quantity);

                    try {
                        // Update the product with the entered details
                        productService.updateProductViaMenu(productId, updatedProduct);
                        System.out.println("Product With ID: " + productId + " Has Been Updated!");
                        validProductId = true;  // Exit the loop, as a valid productId and all other inputs have been obtained
                    } catch (Exception e) {
                        System.out.println("Product Not Updated!!");
                    }

                } else {
                    System.out.println("Product With ID: " + productId + " Does Not Exist.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please Enter A Valid Product ID.");
            }
        }
    }

    // Display existing categories
    public void displayAllCategories() {
        List<Category> existingCategories = categoryService.getAllCategories();
        System.out.println("Existing Categories: ");
        for (Category category : existingCategories) {
            System.out
                    .println("Category ID: " + category.getCategoryId() + " | Category Name: " + category.getCategoryName());
        }
    }

    // Delete A Product By ID
    public void deleteProduct() {
        Scanner input = new Scanner(System.in);

        System.out.println("Delete A Product By ID");
        System.out.println("________________________________________________");
        getAllProducts();
        System.out.println("Enter Product ID: ");
        Long productId = Long.parseLong(input.nextLine());

        boolean exists = productRepository.existsById(productId);
        if (!exists) {
            System.out.println("Product With ID: " + productId + " Does Not Exist.");
        }
        productRepository.deleteById(productId);
        System.out.println("Product With ID: " + productId + " Successfully Deleted.");
    }

}
