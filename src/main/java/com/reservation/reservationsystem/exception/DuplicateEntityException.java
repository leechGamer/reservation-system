package com.reservation.reservationsystem.exception;

import java.io.Serializable;

public class DuplicateEntityException extends CustomException implements Serializable {

    private static final long serialVersionUID = 2391976162842595053L;

    public DuplicateEntityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
