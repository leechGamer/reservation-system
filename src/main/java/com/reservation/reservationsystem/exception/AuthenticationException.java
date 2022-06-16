package com.reservation.reservationsystem.exception;

import java.io.Serializable;

public class AuthenticationException extends RuntimeException implements Serializable {

    private final ErrorCode errorCode;

    public AuthenticationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
