package entity.store;

import entity.*;
import entity.Address;
import entity.Company;
import entity.Location;
import entity.OperationTime;
import entity.StoreCategory;
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

    @Id
    private Long id;

    @Setter
    @Column(nullable = false)
    private String name;

    @ManyToOne(targetEntity = Company.class)
    @JoinColumn(nullable = false, name = "companyId")
    private Company company;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StoreCategory category;

    @Setter
    private String description;

    @Setter
    @Column(nullable = false)
    @Embedded
    private Address address;

    @Setter
    @Embedded
    @Column(nullable = false)
    private Location location;

    @Column(nullable = false)
    private String phonenumber;

    //createdAt, updatedAt Audit에서 가져옴

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
