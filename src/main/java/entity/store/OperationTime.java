package entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Builder
@Table(name = "OperationTime")
public class OperationTime extends Audit{

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "day_of_week")
    private String dayOfWeek;

    @Column(nullable = false, name = "store_id")
    private Long storeId;

    @Column(nullable = false, name = "start_date")
    private LocalDateTime startDate;

    @Column(nullable = false, name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "break_start_time")
    private LocalDateTime breakStartTime;

    @Column(name = "break_end_time")
    private LocalDateTime breakEndTime;


    // 요일 별 영업타임 브레이크타임 변경
    public void setBizTime(String dayOfWeek, LocalDateTime startDate, LocalDateTime endDate){
        this.dayOfWeek = dayOfWeek;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public void setBreakTime(String dayOfWeek, LocalDateTime breakStartTime, LocalDateTime breakEndTime){
        this.dayOfWeek = dayOfWeek;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
    }

    public OperationTime of (
            LocalDateTime startDate,
            LocalDateTime endDate,
            LocalDateTime breakStartTime,
            LocalDateTime breakEndTime
    ){
        return builder()
                .startDate(startDate)
                .endDate(endDate)
                .breakStartTime(breakStartTime)
                .breakEndTime(breakEndTime)
                .build();
    }

}
