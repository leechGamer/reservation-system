package com.reservation.reservationsystem.controller;

import com.reservation.reservationsystem.dto.company.CompanyAddDTO;
import com.reservation.reservationsystem.service.company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping("/companies")
    public ResponseEntity add (@RequestBody @Valid CompanyAddDTO companyAddDTO) {
        companyService.create(companyAddDTO.toEntity());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
