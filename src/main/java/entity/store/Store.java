package entity.store;

import entity.*;
import entity.Location;
import entity.company.Company;
import entity.contstants.StoreCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "store")
public class Store extends Audit {

    @Id @GeneratedValue
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @ManyToOne(targetEntity = Company.class)
    @JoinColumn(nullable = false, name = "company_id")
    private Company company; //com

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreCategory category;

    @Setter
    private String description;

    @Column(nullable = false)
    @Embedded
    private Address address;

    @Embedded
    @Column(nullable = false)
    private Location location;

    @Setter
    @Column(nullable = false, name="phone_number")
    private String phoneNumber;

    @Builder
    public Store of (
            String name,
            StoreCategory storeCategory,
            String description,
            Location location,
            Address address,
            OperationTime operationTime,
            String phoneNumber
    ){
        return builder()
                .name(name)
                .storeCategory(storeCategory)
                .description(description)
                .location(location)
                .address(address)
                .operationTime(operationTime)
                .phoneNumber(phoneNumber)
                .build();
    }
}
