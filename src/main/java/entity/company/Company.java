package entity.company;

import entity.Audit;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Table(name = "company")
public class Company extends Audit {
    @Id
    @GeneratedValue
    @Column(name = "company_id")
    private Long id;

    @Column(length = 13, nullable = false)
    private String businessNumber;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 11, nullable = false)
    private String phoneNumber;

    @Tolerate
    public Company() {}
}
