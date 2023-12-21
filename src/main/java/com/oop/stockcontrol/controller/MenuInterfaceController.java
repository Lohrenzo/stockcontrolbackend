//package com.oop.stockcontrol.controller;
//
//import com.oop.stockcontrol.entity.Product;
//
//import java.util.List;
//import java.util.Scanner;
//
//public class MenuInterfaceController {
//
//    private ProductController productController;
//    private Product product;
//
//    Scanner in = new Scanner(System.in);
//
//    // Declare the selection variable
//    String selection;
//
//    do {
//        System.out.println("--------------------");
//        System.out.println("The Food Store");
//        System.out.println("Choose from these options");
//        System.out.println("--------------------");
//        System.out.println("[1] List All Food Products");
//        System.out.println("[2] Search For Product By SKU");
//        System.out.println("[3] Add A New Product");
//        System.out.println("[4] Update A Product By SKU");
//        System.out.println("[5] Delete A Product By SKU");
//        System.out.println("[6] Exit");
//        System.out.println();
//
//        selection = in.nextLine();
//
//        switch (selection) {
//
//            case "1":
//                // Retrieve and print all products
//                System.out.println("List All Food Products:");
//                List<Product> productList = productController.getAllProducts();
//                for (Product product : productList) {
//                    String productString = product.getSKU() + " | " + product.getName() + " | " + product.getDescription()
//                            + " | "
//                            + product.getCategory() + " | " + product.getPrice();
//                    System.out.println(productString);
//                }
//                System.out.println();
//                break;
//
//            case "2":
//                System.out.println("\nSearch For Product By SKU");
//                // Retrieve a product by its SKU
//                Scanner input = new Scanner(System.in);
//                System.out.print("Input Product's SKU: ");
//                String SKU = input.nextLine().toUpperCase();
//
//                List<Product> productListBySKU = productController.getProductBySKU(SKU);
//                for (Product product : productListBySKU) {
//                    String productString = product.getSKU() + " | " + product.getName() + " | " + product.getDescription()
//                            + " | "
//                            + product.getCategory() + " | " + product.getPrice();
//                    System.out.println(productString);
//                }
//
//                input.close();
//                break;
//
//            case "3":
//                System.out.println("\nAdd A New Product");
//                productController.addNewProduct(product);
//                break;
//
//            case "4":
//                System.out.println("\nUpdate A Product By SKU");
//                break;
//
//            case "5":
//                System.out.println("\nDelete A Product By SKU");
//                break;
//
//            case "6":
//                System.out.println("\nExit");
//                break;
//
//        }
//    } while (!selection.equals("6"));
//
//    // close the input
//    in.;
//}
