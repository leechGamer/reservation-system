package com.reservation.reservationsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_POSSIBLE_CANCEL_RESERVATION(HttpStatus.BAD_REQUEST, "1000", "예약을 취소할 수 없습니다. 예약정보를 확인하세요."),
    NOT_FOUND_ENTITY(HttpStatus.BAD_REQUEST, "1001", "엔티티를 찾을 수 없습니다."),
    DUPLICATE_ENTITY(HttpStatus.CONFLICT, "1002", "이미 등록되었습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "1003", "회원 정보가 일치하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"1004", "권한 정보가 없는 토큰입니다."),
    EMAIL_NOT_FOUND(HttpStatus.FORBIDDEN,"1004", "회원 아이디를 찾지 못했습니다.");

    private HttpStatus status;

    private final String code;

    private final String message;

}
