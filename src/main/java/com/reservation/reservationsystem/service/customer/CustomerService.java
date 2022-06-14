package com.reservation.reservationsystem.service.customer;

import com.reservation.reservationsystem.dto.customer.CustomerSignUpDTO;
import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.exception.DuplicateEntityException;
import com.reservation.reservationsystem.exception.ErrorCode;
import com.reservation.reservationsystem.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer create(final CustomerSignUpDTO customerDTO) {
        final String email = customerDTO.getEmail();

        if (customerRepository.existsByEmail(email)) {
            throw new DuplicateEntityException(ErrorCode.DUPLICATE_ENTITY);
        }

        Customer customer = customerDTO.toEntity();
        return customerRepository.save(customer);
    }
}
