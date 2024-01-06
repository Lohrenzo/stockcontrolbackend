package com.oop.stockcontrol.entity;

import java.util.Date;

public class ProductItem {
    private Long Id;

    private Product product;

    private Date expiryDate;

    public ProductItem(Long id, Product product, Date expiryDate) {
        Id = id;
        this.product = product;
        this.expiryDate = expiryDate;
    }

    public ProductItem() {
    }

    //Getters and Setters

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
