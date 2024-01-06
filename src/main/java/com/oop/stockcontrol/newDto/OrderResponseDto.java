package com.oop.stockcontrol.newDto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class OrderResponseDto {

    private double amount;
    private Integer invoiceNumber;
    private String date;
    private Long orderId;

    // Getters and Setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Integer invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
