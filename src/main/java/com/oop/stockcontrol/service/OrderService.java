package com.oop.stockcontrol.service;

import com.oop.stockcontrol.entity.CartItem;
import com.oop.stockcontrol.entity.Order;
import com.oop.stockcontrol.entity.Product;
import com.oop.stockcontrol.repository.OrderRepository;
import com.oop.stockcontrol.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Order getOrderDetail(Long orderId) {
        Optional<Order> orderOptional = this.orderRepository.findById(orderId);
        return orderOptional.orElse(null);
    }

    public double getCartAmount(List<CartItem> cartItems) {
        double totalCartAmount = 0;
        double singleCartAmount = 0;
        long availableQuantity = 0L;

        for (CartItem cartItem : cartItems) {
            Long productId = cartItem.getProductId();
            Optional<Product> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                if (product.getAvailableQuantity() < cartItem.getQuantity()) {
                    singleCartAmount = product.getPrice() * product.getAvailableQuantity();
                    cartItem.setQuantity(product.getAvailableQuantity());
                } else {
                    singleCartAmount = cartItem.getQuantity() * product.getPrice();
                    availableQuantity = product.getAvailableQuantity() - cartItem.getQuantity();
                }

                totalCartAmount = totalCartAmount + singleCartAmount;
                product.setAvailableQuantity(availableQuantity);
                availableQuantity = 0L;
                cartItem.setProductName(product.getName());
                cartItem.setPrice(singleCartAmount);
                productRepository.save(product);
            }
        }
        return totalCartAmount;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
