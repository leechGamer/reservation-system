package com.reservation.reservationsystem.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reservation.reservationsystem.entity.contstants.ReservationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import java.time.LocalDate;

@Getter
@Builder
public class ReservationSearchRequest {

    private ReservationStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;

    @Tolerate
    ReservationSearchRequest() {}

    @Builder
    public ReservationSearchRequest(ReservationStatus status, LocalDate start, LocalDate end) {
        this.status = status;
        this.start = start;
        this.end = end;
    }
}
