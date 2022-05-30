package com.reservation.reservationsystem.exception;

import java.io.Serializable;

public class NotFoundEntityException extends CustomException implements Serializable {

    private static final long serialVersionUID = -8446244428983684632L;

    public NotFoundEntityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
