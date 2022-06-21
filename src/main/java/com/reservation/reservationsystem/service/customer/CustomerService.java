package com.reservation.reservationsystem.service.customer;

import com.reservation.reservationsystem.dto.customer.CustomerSignUpDTO;
import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.exception.DuplicateEntityException;
import com.reservation.reservationsystem.exception.ErrorCode;
import com.reservation.reservationsystem.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Customer create(final CustomerSignUpDTO customerDTO) {
        final String email = customerDTO.getEmail();

        if (customerRepository.existsByEmail(email)) {
            throw new DuplicateEntityException(ErrorCode.DUPLICATE_ENTITY);
        }

        Customer customer = customerDTO.toEntity();
        return customerRepository.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(ErrorCode.EMAIL_NOT_FOUND.getMessage())
        );
        return User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .roles(customer.getRole().toString())
                .build();
    }
}
