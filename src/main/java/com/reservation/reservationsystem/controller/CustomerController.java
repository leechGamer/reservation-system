package com.reservation.reservationsystem.controller;

import com.reservation.reservationsystem.dto.customer.CustomerSignUpDTO;
import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/signup")
    public ResponseEntity register(@RequestBody @Valid CustomerSignUpDTO customerDTO) {
        Customer customer = this.customerService.create(customerDTO);
        return ResponseEntity.ok().body(customer);
    }
}
