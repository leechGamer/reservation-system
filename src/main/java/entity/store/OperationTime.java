package entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Builder
@Table(name = "OperationTime")
public class OperationTime extends Audit{

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long storeId;

    @Setter
    @Column(nullable = false)
    private Date startDate;

    @Setter
    @Column(nullable = false)
    private Date endDate;

    @Setter
    @Column(nullable = false)
    private Date breakStartTime;

    @Setter
    @Column(nullable = false)
    private Date breakEndTime;

    public OperationTime of (
            Date startDate,
            Date endDate,
            Date breakStartTime,
            Date breakEndTime
    ){
        return builder()
                .startDate(startDate)
                .endDate(endDate)
                .breakStartTime(breakStartTime)
                .breakEndTime(breakEndTime)
                .build();
    }

}
