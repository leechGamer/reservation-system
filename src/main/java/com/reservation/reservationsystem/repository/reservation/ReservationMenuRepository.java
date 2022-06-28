package com.reservation.reservationsystem.repository.reservation;

import com.reservation.reservationsystem.entity.reservation.ReservationMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationMenuRepository extends JpaRepository<ReservationMenu, Long>{
}
