package com.oop.stockcontrol.repository;

import com.oop.stockcontrol.entity.Address;
import com.oop.stockcontrol.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    // SELECT * FROM address where addressId = ?
    @Query("SELECT s FROM Address s WHERE s.addressId = ?1")
    Optional<Address> findAddressById(Long addressId);

}
