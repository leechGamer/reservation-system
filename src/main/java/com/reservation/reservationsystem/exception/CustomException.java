package com.reservation.reservationsystem.exception;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CustomException extends RuntimeException implements Serializable {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
