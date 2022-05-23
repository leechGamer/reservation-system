package com.reservation.reservationsystem.service.reservation;

import com.reservation.reservationsystem.dto.reservation.ReservationSearchRequest;
import com.reservation.reservationsystem.entity.reservation.Reservation;
import com.reservation.reservationsystem.repository.reservation.ReservationRepository;
import com.reservation.reservationsystem.repository.reservation.ReservationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    final private ReservationRepository reservationRepository;

    public List<Reservation> search(Long storeId, ReservationSearchRequest requestDTO) {
        List<Reservation> reservations = reservationRepository.search(storeId, requestDTO);
        return reservations;
    }
}

