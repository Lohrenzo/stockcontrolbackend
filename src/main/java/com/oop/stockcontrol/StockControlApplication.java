package com.oop.stockcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.oop.stockcontrol.entity"})
public class StockControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockControlApplication.class, args);
	}

}
