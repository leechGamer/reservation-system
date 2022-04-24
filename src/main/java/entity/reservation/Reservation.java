package entity.reservation;

import entity.Audit;
import entity.contstants.PaymentType;
import entity.contstants.ReservationStatus;
import entity.customer.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Builder
@Table(name = "reservations")
public class Reservation extends Audit {

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private Customer customer;

    @Setter
    @Enumerated(STRING)
    @Column(length = 40, nullable = false)
    private ReservationStatus reservationStatus; // todo:: default type 지정할 것

    @Column(length = 10, nullable = false)
    private Long amount;

    @Setter
    @Enumerated(STRING)
    @Column(length = 40, nullable = false)
    private PaymentType paymentType; // todo:: default type 지정할 것

    @Setter
    private LocalDateTime paymentDate;

    @Column(nullable = false)
    private LocalDateTime reservedDate;

    @Column(nullable = false)
    private LocalDateTime reservedTime;

    @Column(nullable = false)
    private int numberOfCustomer = 1 ;

    @Tolerate
    public Reservation() {}

    public static Reservation of(
            long customerId,
            ReservationStatus reservationStatus,
            long amount,
            PaymentType paymentType,
            LocalDateTime paymentDate,
            LocalDateTime reservedDate,
            LocalDateTime reservedTime,
            int numberOfCustomer
    ) {
        return builder()
                .reservationStatus(reservationStatus)
                .amount(amount)
                .paymentType(paymentType)
                .paymentDate(paymentDate)
                .reservedDate(reservedDate)
                .reservedTime(reservedTime)
                .numberOfCustomer(numberOfCustomer)
                .build();
    }
}
