package com.reservation.reservationsystem.entity.company;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.reservation.reservationsystem.entity.Audit;
import com.reservation.reservationsystem.entity.store.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@Table(name = "company")
public class Company extends Audit implements Serializable {
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

    @OneToMany
    @JsonManagedReference
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private Set<Store> stores = new HashSet<>();

    @Tolerate
    public Company() {}

    public static Company of(
            String businessNumber,
            String name,
            String phoneNumber
    ) {
        return builder()
                .businessNumber(businessNumber)
                .name(name)
                .phoneNumber(phoneNumber)
                .build();
    }

    public void addStore(Store store) {
        if (store == null) {
            throw new NullPointerException();
        }
        if (stores == null) {
            this.stores = new HashSet<>();
        }
        this.stores.add(store);
        store.setCompany(this);
    }
}
