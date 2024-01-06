//package com.oop.stockcontrol.entity;

//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;

//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "address")
public class Address {
//    @Id
//    @SequenceGenerator(
//            name = "address_sequence",
//            sequenceName = "address_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "address_sequence"
//    )
//    private Long addressId;

//    @Column(nullable = false)
    private String addressLine1;

    private String addressLine2;

//    @Column(nullable = false)
    private String city;

//    @Column(nullable = false)
    private String county;

//    @Column(nullable = false)
    private String postCode;

//    @Column(nullable = false)
    private String country;

//    public Long getAddressId() {
//        return addressId;
//    }
//
//    public void setAddressId(Long addressId) {
//        this.addressId = addressId;
//    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
