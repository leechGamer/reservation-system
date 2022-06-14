package com.reservation.reservationsystem.controller;

import com.reservation.reservationsystem.dto.customer.CustomerSignUpDTO;
import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.service.customer.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/signup")
    public ResponseEntity register(@RequestBody @Valid CustomerSignUpDTO customerDTO) {
        Customer customer = this.customerService.create(customerDTO);
        return ResponseEntity.ok().body(customer);
    }
}
