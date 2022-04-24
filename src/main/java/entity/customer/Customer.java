package entity.customer;

import entity.Audit;
import entity.reservation.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "customers")
public class Customer extends Audit {
    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String email;

    @Column(length = 11, nullable = false)
    private String phoneNumber;

    @Column(length = 10, nullable = false)
    private String password;

    @OneToMany
    @JoinColumn(name = "reservation_id")
    private List<Reservation> reservations = new ArrayList<Reservation>();

    @Tolerate
    public Customer() {}
}

