package entity.store;

import entity.Audit;
import lombok.Builder;
import lombok.Getter;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "operation_time")
public class OperationTime extends Audit {

    @Id @GeneratedValue
    @Column(nullable = false, name = "operation_time_id")
    private Long id;

    @Column(nullable = false, name = "day_of_week")
    private String dayOfWeek;

    @Column(nullable = false, name = "start_time")
    private LocalDateTime startTime;

    @Column(nullable = false, name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "break_start_time")
    private LocalDateTime breakStartTime;

    @Column(name = "break_end_time")
    private LocalDateTime breakEndTime;

    public OperationTime() {}

    @Builder
    public OperationTime of (
            String dayOfWeek,
            LocalDateTime startTime,
            LocalDateTime endTime,
            LocalDateTime breakStartTime,
            LocalDateTime breakEndTime
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
