package com.reservation.reservationsystem.entity.reservation;

import com.reservation.reservationsystem.entity.Audit;
import com.reservation.reservationsystem.entity.company.Company;
import com.reservation.reservationsystem.entity.contstants.PaymentStatus;
import com.reservation.reservationsystem.entity.contstants.PaymentType;
import com.reservation.reservationsystem.entity.contstants.ReservationStatus;
import com.reservation.reservationsystem.entity.customer.Customer;
import com.reservation.reservationsystem.entity.store.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Builder
@Table(name = "reservation")
public class Reservation extends Audit {

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Setter
    @Enumerated(STRING)
    @Column(length = 40, nullable = false)
    private ReservationStatus reservationStatus; // todo:: default type 지정할 것

    @Column(length = 10, nullable = false)
    private Long amount;

    @Setter
    @Enumerated(STRING)
    @Column(length = 40, nullable = false)
    private PaymentStatus paymentStatus;

    @Setter
    @Enumerated(STRING)
    @Column(length = 40, nullable = false)
    private PaymentType paymentType = PaymentType.NONE;

    @Setter
    @Column(nullable = true)
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private LocalDate reservedDate;

    @Column(nullable = false)
    private LocalTime reservedTime;

    @Column(nullable = false)
    private int numberOfCustomer = 1 ;

    @OneToOne
    @JoinColumn(name = "review_id", nullable = true, insertable = false, updatable = false)
    private Review review;

    @Tolerate
    public Reservation() {}

    public static Reservation of(
            ReservationStatus reservationStatus,
            long amount,
            PaymentStatus paymentStatus,
            PaymentType paymentType,
            LocalDateTime paymentDate,
            LocalDate reservedDate,
            LocalTime reservedTime,
            int numberOfCustomer
    ) {
        return builder()
                .reservationStatus(reservationStatus)
                .amount(amount)
                .paymentStatus(paymentStatus)
                .paymentType(paymentType)
                .paymentDate(paymentDate)
                .reservedDate(reservedDate)
                .reservedTime(reservedTime)
                .numberOfCustomer(numberOfCustomer)
                .build();
    }

    public void setCustomer(Customer customer) {
        if (customer == null) {
            throw new EntityExistsException();
        }
        this.customer = customer;
    }

    public void setStore(Store store) {
        if (store == null) {
            throw new EntityExistsException();
        }
        this.store = store;
    }
}
