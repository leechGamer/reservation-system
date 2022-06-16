package com.reservation.reservationsystem.repository;

import com.reservation.reservationsystem.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
