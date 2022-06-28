package com.reservation.reservationsystem.repository.customer;

import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.exception.AuthenticationException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Boolean existsByEmail(String email);
    Optional<Customer> findByEmail(String email) throws AuthenticationException;
    @Override
    Customer getById(Long aLong);
}
