package com.oop.stockcontrol.newDto;

import com.oop.stockcontrol.entity.CartItem;
import com.oop.stockcontrol.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class OrderDto {

    private List<CartItem> cartItems;
    private User user;

    public OrderDto() {
    }

    public OrderDto(List<CartItem> cartItems, User user) {
        this.cartItems = cartItems;
        this.user = user;
    }

    // Getters and Setters

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
