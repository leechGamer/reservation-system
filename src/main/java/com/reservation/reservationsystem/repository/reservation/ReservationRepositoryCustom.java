package com.reservation.reservationsystem.repository.reservation;

import com.reservation.reservationsystem.dto.reservation.ReservationSearchRequest;
import com.reservation.reservationsystem.entity.reservation.Reservation;

import java.util.List;

public interface ReservationRepositoryCustom {
    List<Reservation> search (Long storeId, ReservationSearchRequest request);
}
