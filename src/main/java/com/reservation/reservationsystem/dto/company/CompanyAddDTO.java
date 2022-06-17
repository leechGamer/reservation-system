package com.reservation.reservationsystem.dto.company;

import com.reservation.reservationsystem.entity.company.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.validation.constraints.NotEmpty;

@Getter
public class CompanyAddDTO {

    @NotEmpty(message = "사업자 번호를 입력해 주세요.")
    private String businessNumber;

    @NotEmpty(message = "사업체명을 입력해 주세요.")
    private String name;

    @NotEmpty(message = "전화번호를 입력해 주세요.")
    private String phoneNumber;

    @Tolerate
    public CompanyAddDTO() {}

    @Builder
    public CompanyAddDTO(String businessNumber, String name, String phoneNumber) {
        this.businessNumber = businessNumber;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Company toEntity() {
        return Company.of(businessNumber, name, phoneNumber);
    }
}
