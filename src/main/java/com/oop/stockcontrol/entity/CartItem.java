package com.oop.stockcontrol.entity;


import jakarta.persistence.*;
import lombok.ToString;

@ToString
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartItemId", nullable = false, unique = true)
    private Long cartItemId;

    private Long productId;
    private String productName;

    private Long quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "fk_orderId")
    private Order order;

    public CartItem(Long productId, String productName, Long quantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public CartItem(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CartItem {" +
                "cartId =" + cartItemId +
                ", productId =" + productId +
                ", productName ='" + productName + '\'' +
                ", quantity =" + quantity +
                ", price =" + price +
                '}';
    }
}
