package com.oop.stockcontrol.controller;

import com.oop.stockcontrol.dto.*;
import com.oop.stockcontrol.entity.Category;
import com.oop.stockcontrol.entity.User;
import com.oop.stockcontrol.repository.UserRepository;
import com.oop.stockcontrol.service.auth.UserServiceImpl;
import com.oop.stockcontrol.service.jwt.UserServiceJwtImpl;
import com.oop.stockcontrol.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

//    @Autowired
//    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceJwtImpl userServiceJwt;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    @Autowired
    private UserServiceImpl userServiceImpl;

    // Get All Users REST API
    @GetMapping
    public List<User> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    // Get User By ID REST API
    @GetMapping(path = "details/{userId}")
    public UserDto getUserById(@PathVariable("userId") Long userId) {
        return userServiceImpl.getUserById(userId);
    }

    // Sign Up Customer REST API
    @PostMapping("customer/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupCustomerDto signupCustomerDto) {

        if (userServiceImpl.isPresentByEmail(signupCustomerDto.getEmail())) {
            return new ResponseEntity<>("Customer with email already exists.", HttpStatus.NOT_ACCEPTABLE);
        }

        // Create Object for User DTO
        UserDto createdUser = userServiceImpl.signupCustomer(signupCustomerDto);

        // Encode the password before saving
//        createdUser.setPassword(passwordEncoder.encode(createdUser.getPassword()));

        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    // Sign Up Admin REST API
    @PostMapping("admin/signup")
    public ResponseEntity<?> signupAdmin(@RequestBody SignupAdminDto signupAdminDto) {

        if (userServiceImpl.isPresentByEmail(signupAdminDto.getEmail())) {
            return new ResponseEntity<>("Admin with email already exists.", HttpStatus.NOT_ACCEPTABLE);
        }

        // Create Object for User DTO
        UserDto createdUser = userServiceImpl.signupAdmin(signupAdminDto);

        // Encode the password before saving
//        createdUser.setPassword(passwordEncoder.encode(createdUser.getPassword()));

        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @PostMapping({"/authenticate"})
    public void createAuthenticationToken(@RequestBody AuthRequestDto authenticationRequest,
                                          HttpServletResponse response) throws IOException, JSONException {
//    public ResponseEntity<AuthResponseDto> createAuthenticationToken(@RequestBody AuthRequestDto authenticationRequest,
//                                                                     HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()
            ));
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect Username or Password", e);
        }

        final UserDetails userDetails = userServiceJwt.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        Optional<User> userOptional = userRepository.findByUsername(authenticationRequest.getUsername());
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        response.getWriter().write(new JSONObject()
                .put("userId", user.getUserId())
                .put("username", user.getUsername())
                .put("role", user.getRole())
                .toString()
        );

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Expose-Headers", "Authorization," +
                " X-PINGOTHER, Origin, X-Requested_With, Content-Type, Accept, X-Customer-header");

        // Response header will contain the generated jwt
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);

//        return new ResponseEntity<>("User login success", HttpStatus.OK);
    }
}
