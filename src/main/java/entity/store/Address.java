package entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@Getter
@Builder
public class Address {
    @Column(nullable = false, name = "address")
    private String address;

    @Column(nullable = false, name = "zipcode")
    private String zipcode;

    public Address of (
            String address,
            String zipcode
    ){
        return builder()
                .address(address)
                .zipcode(zipcode)
                .build();
    }
}
