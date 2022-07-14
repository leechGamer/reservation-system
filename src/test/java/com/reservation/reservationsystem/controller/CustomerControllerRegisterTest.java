package com.reservation.reservationsystem.controller;

import com.google.gson.Gson;
import com.reservation.reservationsystem.dto.customer.CustomerSignUpDTO;
import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.entity.customer.UserRoleType;
import com.reservation.reservationsystem.service.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CustomerControllerRegisterTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private MockMvc mockMvc;

    private CustomerSignUpDTO signUpDTO;

    private Gson gson;

    private Customer customer;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
        gson = new Gson();

        String name = "name";
        String email = "id@gmail.com";
        String phoneNumber = "01012341234";
        String password = new BCryptPasswordEncoder().encode("password");

        signUpDTO = CustomerSignUpDTO.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .userRole(UserRoleType.BUSINESS.toString())
                .build();
        customer = Customer.of(name, email, phoneNumber, password, UserRoleType.BUSINESS.toString());
    }

    @Test
    @DisplayName("회원 가입")
    public void test() throws Exception {
        // Given
        given(customerService.create(any(CustomerSignUpDTO.class))).willReturn(any(Customer.class));

        // When & Then
        mockMvc.perform(
                post("/auth/signup")
                        .content(gson.toJson(signUpDTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
