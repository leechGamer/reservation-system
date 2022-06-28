package com.reservation.reservationsystem.repository.reservation;

import com.reservation.reservationsystem.dto.reservation.ReservationSearchRequestDTO;
import com.reservation.reservationsystem.entity.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryCustom {
    @Override
    List<Reservation> search(Long storeId, ReservationSearchRequestDTO request);
}
