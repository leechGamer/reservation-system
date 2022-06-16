package com.reservation.reservationsystem.controller;

import com.reservation.reservationsystem.config.TokenProvider;
import com.reservation.reservationsystem.dto.customer.CustomerSignInDTO;
import com.reservation.reservationsystem.dto.customer.CustomerSignUpDTO;
import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private final TokenProvider tokenProvider;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity register(@RequestBody @Valid CustomerSignUpDTO customerDTO) {
        Customer customer = this.customerService.create(customerDTO);
        return ResponseEntity.ok().body(customer);
    }

    @PostMapping("/signin")
    public ResponseEntity login(@RequestBody @Valid CustomerSignInDTO customerDTO) {
        final String email = customerDTO.getEmail();
        final String password = customerDTO.getPassword();

        Customer customer = this.customerService.getByCredentials(email, password);

        final String token = tokenProvider.create(customer);
        return ResponseEntity.ok().body(token);
    }
}
