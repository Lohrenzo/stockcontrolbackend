package com.oop.stockcontrol.controller;

import com.oop.stockcontrol.newDto.OrderDto;
import com.oop.stockcontrol.newDto.OrderResponseDto;
import com.oop.stockcontrol.entity.Order;
import com.oop.stockcontrol.entity.User;
import com.oop.stockcontrol.service.OrderService;
import com.oop.stockcontrol.service.ProductService;
import com.oop.stockcontrol.service.auth.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@RestController
@RequestMapping(path = "api/order")
public class CartController {

    private final OrderService orderService;
//    private final ProductService productService;
//    private UserServiceImpl userService;

    public CartController(
            OrderService orderService
//            ProductService productService,
//            UserServiceImpl userService
    ) {
        this.orderService = orderService;
//        this.productService = productService;
//        this.userService = userService;
    }

    private final Logger logger = LoggerFactory.getLogger(CartController.class);

    @GetMapping(value = "details/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable Long orderId) {
        Order order = orderService.getOrderDetail(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping
//    @PostMapping("/placeOrder")
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody OrderDto orderDto) {
        logger.info("Request Payload " + orderDto.toString());
        OrderResponseDto orderResponseDto = new OrderResponseDto();

        double amount = orderService.getCartAmount(orderDto.getCartItems());

        // Convert OrderDto to Order
        Order order = new Order();
        order.setCartItems(orderDto.getCartItems());

        // Set the user from the orderDto
        User user = new User();
        user.setUserId(orderDto.getUser().getUserId());
        order.setUser(user);

        order = orderService.saveOrder(order);
        logger.info("Order processed successfully");

        // Set Date and Time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        orderResponseDto.setDate(dtf.format(now));

        // Set other fields
        orderResponseDto.setOrderId(order.getOrderId());
        orderResponseDto.setAmount(amount);
        orderResponseDto.setInvoiceNumber(new Random().nextInt(1000));

        logger.info("Response Has Been Set");

        return ResponseEntity.ok(orderResponseDto);
    }
}
