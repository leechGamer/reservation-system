package entity.store;

import lombok.Builder;
import lombok.Getter;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    @Column(nullable = false, name = "address")
    private String address;

    @Column(nullable = false, name = "zip_code")
    private String zipcode;

    public Address(){}

    @Builder
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
