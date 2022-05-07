package com.reservation.reservationsystem.entity.store;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
public class Address {
    @Column(nullable = false, name = "address")
    private String address;

    @Column(nullable = false, name = "zip_code")
    private String zipcode;

    @Tolerate
    public Address(){}

    public static Address of (
            String address,
            String zipcode
    ){
        return builder()
                .address(address)
                .zipcode(zipcode)
                .build();
    }
}
