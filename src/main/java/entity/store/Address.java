package entity.store;

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

    @Column(nullable = false, name = "zip_code")
    private String zipcode;

    public Address(String address, String zipcode){
        this.address = address;
        this.zipcode = zipcode;
    }

    public void setAddress(String address, String zipcode){
        this.address = address;
        this.zipcode = zipcode;
    }

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
