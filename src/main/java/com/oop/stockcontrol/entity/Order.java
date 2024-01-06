package com.oop.stockcontrol.entity;

import jakarta.persistence.*;
import lombok.ToString;

import java.util.List;

@ToString
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String orderDescription;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_userId", referencedColumnName = "userId")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = CartItem.class)
    private List<CartItem> cartItems;

    public Order() {
    }

    public Order(
            Long orderId,
//            String orderDescription,
            User user,
            List<CartItem> cartItems
    ) {
        this.orderId = orderId;
//        this.orderDescription = orderDescription;
        this.user = user;
        this.cartItems = cartItems;
    }

    // Getters and Setters

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

//    public String getOrderDescription() {
//        return orderDescription;
//    }
//
//    public void setOrderDescription(String orderDescription) {
//        this.orderDescription = orderDescription;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
