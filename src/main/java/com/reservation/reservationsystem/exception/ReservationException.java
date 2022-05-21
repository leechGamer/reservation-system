package com.reservation.reservationsystem.exception;

import java.io.Serializable;

public class ReservationException extends CustomException implements Serializable {

    private static final long serialVersionUID = 7165230430080844303L;

    public ReservationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
