package com.oop.stockcontrol.service.auth;

//import com.oop.stockcontrol.dto.*;
import com.oop.stockcontrol.entity.User;
//import com.oop.stockcontrol.entity.User;
import com.oop.stockcontrol.newDto.*;
import com.oop.stockcontrol.enums.UserRole;
import com.oop.stockcontrol.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

//    private Address createAddress(AddressDto addressDto) {
//        Address address = new Address();
//        address.setAddressLine1(addressDto.getAddressLine1());
//        address.setAddressLine2(addressDto.getAddressLine2());
//        address.setCity(addressDto.getCity());
//        address.setCounty(addressDto.getCounty());
//        address.setPostCode(addressDto.getPostCode());
//        address.setCountry(addressDto.getCountry());
//        return addressRepository.save(address);
//    }

    // Create A New User With Role CUSTOMER
    public UserDto signupCustomer(SignupCustomerDto signupCustomerDto) {
        // Create new customer user
        User user = new User();

        // Add Data to the new Customer User
        user.setEmail(signupCustomerDto.getEmail());
        user.setUsername(signupCustomerDto.getUsername());
        user.setBusinessName(signupCustomerDto.getBusinessName());
        user.setPhoneNumber(signupCustomerDto.getPhoneNumber());

        // Set and encrypt password
//        user.setPassword(signupCustomerDto.getPassword());
        user.setPassword(passwordEncoder.encode(signupCustomerDto.getPassword()));

        // Set Address
        user.setAddressLine1(signupCustomerDto.getAddressLine1());
        user.setAddressLine2(signupCustomerDto.getAddressLine2());
        user.setCity(signupCustomerDto.getCity());
        user.setCounty(signupCustomerDto.getCounty());
        user.setPostCode(signupCustomerDto.getPostCode());
        user.setCountry(signupCustomerDto.getCountry());

        // Set Role to Customer
        user.setRole(UserRole.CUSTOMER);

        // Save the user and retrieve the saved entity
        User savedUser = userRepository.save(user);

        return savedUser.getDto();
    }

    // Create A New User With Role ADMIN
    public UserDto signupAdmin(SignupAdminDto signupAdminDto) {
        // Create new admin user
        User user = new User();

        // Add Data to the new Admin User
        user.setEmail(signupAdminDto.getEmail());
        user.setUsername(signupAdminDto.getUsername());
        user.setFirstName(signupAdminDto.getFirstName());
        user.setLastName(signupAdminDto.getLastName());
        user.setPhoneNumber(signupAdminDto.getPhoneNumber());

        // Set and encrypt password
        user.setPassword(passwordEncoder.encode(signupAdminDto.getPassword()));

        // Set Address
        user.setAddressLine1(signupAdminDto.getAddressLine1());
        user.setAddressLine2(signupAdminDto.getAddressLine2());
        user.setCity(signupAdminDto.getCity());
        user.setCounty(signupAdminDto.getCounty());
        user.setPostCode(signupAdminDto.getPostCode());
        user.setCountry(signupAdminDto.getCountry());

        // Set Role to Admin
        user.setRole(UserRole.ADMIN);

        // Save the user and retrieve the saved entity
        User savedUser = userRepository.save(user);

        // Use the saved entity to construct and return the UserDto
        return savedUser.getDto();
    }

    // Update A User By ID
    @Transactional
    public UserDto updateUser(Long userId, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with Id: " + userId + " does not exist."));

        // Update username if provided and not equal to the current username
        if (updateUserDto.getUsername() != null &&
                !updateUserDto.getUsername().isEmpty() &&
                !Objects.equals(user.getUsername(), updateUserDto.getUsername())) {
            validateUsername(updateUserDto.getUsername());
            user.setUsername(updateUserDto.getUsername());
        }

        // Update Phone Number
        if (updateUserDto.getPhoneNumber() != null && !Objects.equals(user.getPhoneNumber(), updateUserDto.getPhoneNumber())) {
            user.setPhoneNumber(updateUserDto.getPhoneNumber());
        } else if (updateUserDto.getPhoneNumber() == null || Objects.equals(user.getPhoneNumber(), updateUserDto.getPhoneNumber())) {
            user.setPhoneNumber(user.getPhoneNumber());
        }

        // Update Email
        if (updateUserDto.getEmail() != null && !Objects.equals(user.getEmail(), updateUserDto.getEmail())) {
            user.setEmail(updateUserDto.getEmail());
        } else if (updateUserDto.getEmail() == null || Objects.equals(user.getEmail(), updateUserDto.getEmail())) {
            user.setEmail(user.getEmail());
        }

        // Update Role
        if (updateUserDto.getRole() != null && !Objects.equals(user.getRole(), updateUserDto.getRole())) {
            user.setRole(updateUserDto.getRole());
        } else if (updateUserDto.getRole() == null || Objects.equals(user.getRole(), updateUserDto.getRole())) {
            user.setRole(user.getRole());
        }

        // Update Business Name
        if (updateUserDto.getBusinessName() != null && !Objects.equals(user.getBusinessName(), updateUserDto.getBusinessName())) {
            user.setBusinessName(updateUserDto.getBusinessName());
        } else if (updateUserDto.getBusinessName() == null || Objects.equals(user.getBusinessName(), updateUserDto.getBusinessName())) {
            user.setBusinessName(user.getBusinessName());
        }

        // Update First Name
        if (updateUserDto.getFirstName() != null && !Objects.equals(user.getFirstName(), updateUserDto.getFirstName())) {
            user.setFirstName(updateUserDto.getFirstName());
        } else if (updateUserDto.getFirstName() == null || Objects.equals(user.getFirstName(), updateUserDto.getFirstName())) {
            user.setFirstName(user.getFirstName());
        }

        // Update Last Name
        if (updateUserDto.getLastName() != null && !Objects.equals(user.getLastName(), updateUserDto.getLastName())) {
            user.setLastName(updateUserDto.getLastName());
        } else if (updateUserDto.getLastName() == null || Objects.equals(user.getLastName(), updateUserDto.getLastName())) {
            user.setLastName(user.getLastName());
        }

        // Update password only if a new password is provided
        updatePassword(user, updateUserDto.getPassword());

        // Update address fields if provided
        if (updateUserDto.getAddressLine1() != null && !Objects.equals(user.getAddressLine1(), updateUserDto.getAddressLine1())) {
            user.setAddressLine1(updateUserDto.getAddressLine1());
        } else if (updateUserDto.getAddressLine1() == null || Objects.equals(user.getAddressLine1(), updateUserDto.getAddressLine1())) {
            user.setAddressLine1(user.getAddressLine1());
        }

        if (updateUserDto.getAddressLine2() != null && !Objects.equals(user.getAddressLine2(), updateUserDto.getAddressLine2())) {
            user.setAddressLine2(updateUserDto.getAddressLine2());
        } else if (updateUserDto.getAddressLine2() == null || Objects.equals(user.getAddressLine2(), updateUserDto.getAddressLine2())) {
            user.setAddressLine2(user.getAddressLine2());
        }

        if (updateUserDto.getCity() != null && !Objects.equals(user.getCity(), updateUserDto.getCity())) {
            user.setCity(updateUserDto.getCity());
        } else if (updateUserDto.getCity() == null || Objects.equals(user.getCity(), updateUserDto.getCity())) {
            user.setCity(user.getCity());
        }

        if (updateUserDto.getCounty() != null && !Objects.equals(user.getCounty(), updateUserDto.getCounty())) {
            user.setCounty(updateUserDto.getCounty());
        } else if (updateUserDto.getCounty() == null || Objects.equals(user.getCounty(), updateUserDto.getCounty())) {
            user.setCounty(user.getCounty());
        }

        if (updateUserDto.getPostCode() != null && !Objects.equals(user.getPostCode(), updateUserDto.getPostCode())) {
            user.setPostCode(updateUserDto.getPostCode());
        } else if (updateUserDto.getPostCode() == null || Objects.equals(user.getPostCode(), updateUserDto.getPostCode())) {
            user.setPostCode(user.getPostCode());
        }

        if (updateUserDto.getCountry() != null && !Objects.equals(user.getCountry(), updateUserDto.getCountry())) {
            user.setCountry(updateUserDto.getCountry());
        } else if (updateUserDto.getCountry() == null || Objects.equals(user.getCountry(), updateUserDto.getCountry())) {
            user.setCountry(user.getCountry());
        }

        // Save the updated user
        User savedUser = userRepository.save(user);

        // Return the updated user DTO
        return savedUser.getDto();
    }

    // Validate and throw exception if the username is already taken
    private void validateUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            throw new IllegalStateException("User with Username: " + username + " already exists.");
        }
    }

    // Update password only if a new password is provided
    private void updatePassword(User user, String newPassword) {
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        } else if (newPassword == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }

    // Update address if provided
//    private void updateAddress(User user, AddressDto addressDto) {
//        if (addressDto != null) {
//            Address address = user.getAddress();
//            if (address == null) {
//                address = createAddress(addressDto);
//                user.setAddress(address);
//            } else {
//                // Update existing address
//                address.setAddressLine1(addressDto.getAddressLine1());
//                address.setAddressLine2(addressDto.getAddressLine2());
//                address.setCity(addressDto.getCity());
//                address.setCounty(addressDto.getCounty());
//                address.setPostCode(addressDto.getPostCode());
//                address.setCountry(addressDto.getCountry());
//            }
//        }
//    }

//    public UserDto updateUser(Long userId, UpdateUserDto updateUserDto) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(
//                        () -> new IllegalStateException("User with Id: " + userId.toString() + " does not exist.")
//                );
//
//        // Update username
//        if (updateUserDto.getUsername() != null && !updateUserDto.getUsername().isEmpty() && !Objects.equals(user.getUsername(), updateUserDto.getUsername())) {
//            Optional<User> userOptional = userRepository.findByUsername(updateUserDto.getUsername());
//            if (userOptional.isPresent()) {
//                throw new IllegalStateException("User With Username: " + updateUserDto.getUsername() + " already exists.");
//            }
//            user.setUsername(updateUserDto.getUsername());
//        }
//
//        // Update other fields
//        user.setPhoneNumber(updateUserDto.getPhoneNumber());
//        user.setEmail(updateUserDto.getEmail());
//        user.setRole(updateUserDto.getRole());
//        user.setBusinessName(updateUserDto.getBusinessName());
//        user.setFirstName(updateUserDto.getFirstName());
//        user.setLastName(updateUserDto.getLastName());
//
//        // Update password only if a new password is provided
//        if (updateUserDto.getPassword() != null && !updateUserDto.getPassword().isEmpty()) {
//            user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
//        }
//
//        // Update address if provided
//        if (updateUserDto.getAddressDto() != null) {
//            Address address = user.getAddress();
//            if (address == null) {
//                address = createAddress(updateUserDto.getAddressDto());
//                user.setAddress(address);
//            } else {
//                // Update existing address
//                address.setAddressLine1(updateUserDto.getAddressDto().getAddressLine1());
//                address.setAddressLine2(updateUserDto.getAddressDto().getAddressLine2());
//                address.setCity(updateUserDto.getAddressDto().getCity());
//                address.setCounty(updateUserDto.getAddressDto().getCounty());
//                address.setPostCode(updateUserDto.getAddressDto().getPostCode());
//                address.setCountry(updateUserDto.getAddressDto().getCountry());
//            }
//        }
//
//        // Save the updated user
//        userRepository.save(user);
//
//        // Return the updated user DTO
//        return user.getDto();
//    }

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
