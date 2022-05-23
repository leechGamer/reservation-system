package com.reservation.reservationsystem.entity.store;

import com.reservation.reservationsystem.entity.Audit;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@Table(name = "operation_time")
public class OperationTime extends Audit {

    @Id @GeneratedValue
    @Column(nullable = false, name = "operation_time_id")
    private Long id;

    @Column(nullable = false, name = "day_of_week")
    private String dayOfWeek;

    @Column(nullable = false, name = "start_time")
    private LocalTime startTime;

    @Column(nullable = false, name = "end_time")
    private LocalTime endTime;

    @Column(name = "break_start_time")
    private LocalTime breakStartTime;

    @Column(name = "break_end_time")
    private LocalTime breakEndTime;

    @Tolerate
    public OperationTime() {}

    public static OperationTime of (
            String dayOfWeek,
            LocalTime startTime,
            LocalTime endTime,
            LocalTime breakStartTime,
            LocalTime breakEndTime
    ){
        return builder()
                .dayOfWeek(dayOfWeek)
                .startTime(startTime)
                .endTime(endTime)
                .breakStartTime(breakStartTime)
                .breakEndTime(breakEndTime)
                .build();
    }

}
