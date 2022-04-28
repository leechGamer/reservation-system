package entity.reservation;

import javax.persistence.*;

@Entity
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
