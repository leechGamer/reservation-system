package com.reservation.reservationsystem.entity.contstants;

public enum ReservationStatus {
    CONFIRMED, // 예약 승인
    PENDING, // 예약 승인 중
    CANCELED, // 예약 취소
    PAID, // 결제가 완료 된 예약
    EXPIRED // 만료된 예약
}
