package com.reservation.reservationsystem.dto.customer;

import com.reservation.reservationsystem.entity.customer.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
public class CustomerSignUpDTO {

    @NotEmpty(message = "이름을 입력하세요.")
    private String name;

    @Email
    @NotEmpty(message = "email을 입력하세요.")
    private String email;

    @NotEmpty(message = "휴대폰 번호를 입력하세요.")
    private String phoneNumber;

    @NotEmpty
    private String password;

    @NotEmpty
    private String userRole;

    @Tolerate
    CustomerSignUpDTO() {}

    @Builder
    public CustomerSignUpDTO (
            String name,
            String email,
            String phoneNumber,
            String password,
            String userRole
    ) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userRole = userRole;
    }

    public Customer toEntity() {
        return Customer.of(name, email, phoneNumber, password, userRole);
    }
}
