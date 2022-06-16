package com.reservation.reservationsystem.service.customer;

import com.reservation.reservationsystem.dto.customer.CustomerSignUpDTO;
import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.exception.AuthenticationException;
import com.reservation.reservationsystem.exception.DuplicateEntityException;
import com.reservation.reservationsystem.exception.ErrorCode;
import com.reservation.reservationsystem.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

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

    public Customer getByCredentials(final String email, final String password) throws AuthenticationException {
        Customer customer = customerRepository.findByEmail(email)
                //.filter(c -> encoder.matches(password, c.getPassword()))
                .orElseThrow(
                        () -> new AuthenticationException(ErrorCode.UNAUTHORIZED)
                );
        return customer;
    }
}
