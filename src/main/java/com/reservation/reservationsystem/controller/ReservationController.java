package com.reservation.reservationsystem.controller;

import com.reservation.reservationsystem.dto.reservation.ReservationSearchRequest;
import com.reservation.reservationsystem.entity.contstants.ReservationStatus;
import com.reservation.reservationsystem.entity.reservation.Reservation;
import com.reservation.reservationsystem.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.reservation.reservationsystem.exception.ErrorCode;
import com.reservation.reservationsystem.exception.ReservationException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/business/stores/reservations/{storeId}")
    public ResponseEntity search(
            @PathVariable(name = "storeId") Long storeId,
            @RequestParam(name = "status", required = false) ReservationStatus status,
            @RequestParam(name = "start", required = false) LocalDate start,
            @RequestParam(name = "end", required = false) LocalDate end
    ) {
        ReservationSearchRequest request = ReservationSearchRequest.builder()
                .status(status)
                .start(start)
                .end(end)
                .build();

        List<Reservation> reservations = this.reservationService.search(storeId, request);

        return ResponseEntity.status(HttpStatus.OK).body(reservations);
    }

    @PutMapping("/business/stores/reservations/{reservationId}")
    public ResponseEntity cancel(@PathVariable(name = "reservationId") Long reservationId) {
            reservationService.cancel(reservationId);
            return new ResponseEntity<>(HttpStatus.OK);
    }
}
