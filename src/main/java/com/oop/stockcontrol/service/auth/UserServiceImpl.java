package com.oop.stockcontrol.service.auth;

import com.oop.stockcontrol.dto.AddressDto;
import com.oop.stockcontrol.dto.SignupAdminDto;
import com.oop.stockcontrol.dto.SignupCustomerDto;
import com.oop.stockcontrol.dto.UserDto;
import com.oop.stockcontrol.entity.Address;
import com.oop.stockcontrol.entity.Category;
import com.oop.stockcontrol.entity.User;
import com.oop.stockcontrol.enums.UserRole;
import com.oop.stockcontrol.repository.AddressRepository;
import com.oop.stockcontrol.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private Address createAddress(AddressDto addressDto) {
        Address address = new Address();
        address.setAddressLine1(addressDto.getAddressLine1());
        address.setAddressLine2(addressDto.getAddressLine2());
        address.setCity(addressDto.getCity());
        address.setCounty(addressDto.getCounty());
        address.setPostCode(addressDto.getPostCode());
        address.setCountry(addressDto.getCountry());
        return addressRepository.save(address);
    }


    public UserDto signupCustomer(SignupCustomerDto signupCustomerDto) {
        // Create new customer user
        User user = new User();

        // Add Date to the new Customer User
        user.setEmail(signupCustomerDto.getEmail());
        user.setUsername(signupCustomerDto.getUsername());
        user.setBusinessName(signupCustomerDto.getBusinessName());
        user.setPhoneNumber(signupCustomerDto.getPhoneNumber());
//        user.setPassword(signupCustomerDto.getPassword());
        user.setPassword(passwordEncoder.encode(signupCustomerDto.getPassword()));

        // Create and set the address
        Address address = createAddress(signupCustomerDto.getAddress());
        user.setAddress(address);

        // Set Role to Customer
        user.setRole(UserRole.CUSTOMER);

        // Save the user and retrieve the saved entity
        User savedUser = userRepository.save(user);

        return savedUser.getDto();
    }

    public UserDto signupAdmin(SignupAdminDto signupAdminDto) {
        // Create new admin user
        User user = new User();

        // Add Date to the new Admin User
        user.setEmail(signupAdminDto.getEmail());
        user.setUsername(signupAdminDto.getUsername());
        user.setFirstName(signupAdminDto.getFirstName());
        user.setLastName(signupAdminDto.getLastName());
        user.setPhoneNumber(signupAdminDto.getPhoneNumber());
//        user.setPassword(signupAdminDto.getPassword());
        user.setPassword(passwordEncoder.encode(signupAdminDto.getPassword()));

        // Create and set the address
        Address address = createAddress(signupAdminDto.getAddress());
        user.setAddress(address);

        // Set Role to Admin
        user.setRole(UserRole.ADMIN);

        // Save the user and retrieve the saved entity
        User savedUser = userRepository.save(user);

        // Use the saved entity to construct and return the UserDto
        return savedUser.getDto();
    }

    // Get User By User ID
    public UserDto getUserById (Long userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getDto();
        }
        throw new IllegalStateException("User with Id: " + userId + " does not exist.");
    }

    // Get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Boolean isPresentByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
