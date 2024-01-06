package com.oop.stockcontrol.service.auth;

import com.oop.stockcontrol.newDto.*;
//import com.oop.stockcontrol.dto.SignupAdminDto;
//import com.oop.stockcontrol.dto.SignupCustomerDto;
//import com.oop.stockcontrol.dto.UserDto;

public interface UserService {
    UserDto signupCustomer(SignupCustomerDto signupCustomerDto);

    UserDto signupAdmin(SignupAdminDto signupAdminDto);

    Boolean isPresentByEmail(String email);
}
