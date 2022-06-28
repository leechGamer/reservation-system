package com.reservation.reservationsystem.dto.reservation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationRequestDTO {

    @NotNull
    private long storeId;

    @Nullable
    private long menuId;

    @Nullable
    private long amount;

    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime reservedDateTime;

    @NotNull(message = "인원을 입력해주세요.")
    @DecimalMin(value = "1.00", message = "인원은 1명이상으로 입력해주세요")
    private int numberOfCustomer;

    @Tolerate
    ReservationRequestDTO() {}
}
