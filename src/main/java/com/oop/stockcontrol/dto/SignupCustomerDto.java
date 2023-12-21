package com.oop.stockcontrol.dto;

import com.oop.stockcontrol.entity.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupCustomerDto {
    private Long userId;

    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Business name is required")
    private String businessName;

    private String phoneNumber;

    private AddressDto address;

    private String password;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public AddressDto getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
