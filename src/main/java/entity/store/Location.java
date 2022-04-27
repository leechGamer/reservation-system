package entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
@Builder
public class Location {

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLocation (double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

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

