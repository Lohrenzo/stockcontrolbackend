package com.oop.stockcontrol.config;

import com.oop.stockcontrol.enums.UserRole;
import com.oop.stockcontrol.service.jwt.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .exceptionHandling()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(
//                        "/api/users/authenticate",
//                        "/api/users/customer/signup",
//                        "/api/users/admin/signup",
                        "/api/users/**",
                        "/api/products/**",
                        "/api/categories/**"
//                        "/api/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
//                .authorizeHttpRequests()
//                .requestMatchers(
//                        "/api/products/create",
//                        "/api/products/update/**",
//                        "/api/products/delete/**",
//                        "/api/categories/create",
//                        "/api/categories/update/**",
//                        "/api/categories/delete/**"
//                )
////                .permitAll()
//                .hasRole(String.valueOf(UserRole.ADMIN))
//                .and()

                // Disable session by rendering it stateless
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .and()

                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
