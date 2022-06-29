package com.reservation.reservationsystem.service.customer;

import com.reservation.reservationsystem.config.QuerydslConfiguration;
import com.reservation.reservationsystem.dto.customer.CustomerSignUpDTO;
import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.entity.customer.UserRoleType;
import com.reservation.reservationsystem.repository.customer.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfiguration.class)
@DataJpaTest
class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository repository;

    private CustomerSignUpDTO signUpDTO;

    private String name;

    private String phoneNumber;

    private String password;

    private String email;

    @BeforeEach
    void setUp() {
        name = "name";
        email = "id@gmail.com";
        phoneNumber = "01012341234";
        password = "password";

        signUpDTO = CustomerSignUpDTO.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(password)
                .userRole(UserRoleType.NORMAL.toString())
                .build();
    }

    @Test
    @DisplayName("회원가입")
    public void test() {
        // Given
        Customer expected = signUpDTO.toEntity();

        given(repository.save(any())).willReturn(expected);

        // When
        Customer customer = customerService.create(signUpDTO);

        // Then
        Assertions.assertEquals(customer.getEmail(), email);
        Assertions.assertEquals(customer.getName(), name);
        Assertions.assertNotNull(customer.getPassword());
    }
}
