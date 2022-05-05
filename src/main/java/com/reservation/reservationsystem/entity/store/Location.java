package com.reservation.reservationsystem.entity.store;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Builder
public class Location {

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Tolerate
    public Location(){}

    public Location of (
            Double latitude,
            Double longitude
    ){
        return builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
