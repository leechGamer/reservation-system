package com.reservation.reservationsystem.entity.reservation;

import com.reservation.reservationsystem.entity.Audit;
import com.reservation.reservationsystem.entity.menu.Menu;
import lombok.Builder;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Table(name = "reservation_menu")
public class ReservationMenu extends Audit implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "reservation_menu_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Tolerate
    public ReservationMenu() {}

    public static ReservationMenu of(
            Reservation reservation,
            Menu menu
    ) {
        return builder()
                .reservation(reservation)
                .menu(menu)
                .build();
    }
}
