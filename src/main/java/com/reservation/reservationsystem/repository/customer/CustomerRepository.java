package com.reservation.reservationsystem.repository.customer;

import com.reservation.reservationsystem.entity.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Boolean existsByEmail(String email);
}
