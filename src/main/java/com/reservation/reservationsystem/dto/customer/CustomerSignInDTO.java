package com.reservation.reservationsystem.dto.customer;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
public class CustomerSignInDTO {

    @Email
    @NotEmpty(message = "email을 입력하세요.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력하세요.")
    private String password;
}
