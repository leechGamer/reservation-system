package com.reservation.reservationsystem.entity.customer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.reservation.reservationsystem.entity.Audit;
import com.reservation.reservationsystem.entity.reservation.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Builder
@Table(name = "customer")
public class Customer extends Audit implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 11, nullable = false)
    private String phoneNumber;

    @Column(length = 65, nullable = false)
    private String password;

    @Column(length = 15, nullable = false)
    @Enumerated(STRING)
    private UserRoleType role;

    @Builder.Default
    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Reservation> reservations = new ArrayList<Reservation>();

    @Tolerate
    public Customer() {}

    public static Customer of (
            String name,
            String email,
            String phoneNumber,
            String password,
            String userRole
    ) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .password(passwordEncoder.encode(password))
                .role(UserRoleType.valueOf(userRole))
                .build();
    }

    public void addReservation(Reservation reservation) {
        if (reservation == null) {
            throw new EntityExistsException();
        }
        if (reservations == null) {
            this.reservations = new ArrayList<>();
        }
        this.reservations.add(reservation);
    }
}

