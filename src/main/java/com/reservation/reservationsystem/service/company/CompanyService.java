package com.reservation.reservationsystem.service.company;

import com.reservation.reservationsystem.entity.company.Company;
import com.reservation.reservationsystem.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public void create(Company company) {
        companyRepository.save(company);
    }
}
