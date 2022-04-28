package entity.store;

import lombok.Builder;
import lombok.Getter;
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

