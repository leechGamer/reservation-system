package com.reservation.reservationsystem.entity.reservation;

import com.reservation.reservationsystem.entity.menu.Menu;

import javax.persistence.*;

@Entity
@Table(name = "reservation_menu")
public class ReservationMenu {

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
}
