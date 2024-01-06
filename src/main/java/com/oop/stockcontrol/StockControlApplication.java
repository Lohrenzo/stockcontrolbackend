package com.oop.stockcontrol;

import com.oop.stockcontrol.controller.MenuConsoleController;
import com.oop.stockcontrol.entity.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
@EntityScan(basePackages = {"com.oop.stockcontrol.entity"})
public class StockControlApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StockControlApplication.class, args);

        final int EXIT_OPTION_MENU = 6;
        MenuConsoleController menuConsoleController = context.getBean(MenuConsoleController.class);

        int option;

        do {
            option = menuConsoleController.showMenu();
            switch (option) {
                case 1:
                    menuConsoleController.getAllProducts();
                    break;

                case 2:
                    menuConsoleController.getProductById();
                    break;

                case 3:
                    menuConsoleController.addNewProduct();
                    break;

                case 4:
                    menuConsoleController.updateProductById();
                    break;

                case 5:
                    menuConsoleController.deleteProduct();
                    break;

                default:
                    System.out.println("Option " + option + " not found");
            }
        } while (option != EXIT_OPTION_MENU);

        // Close the application context when done
        context.close();
	}

}
